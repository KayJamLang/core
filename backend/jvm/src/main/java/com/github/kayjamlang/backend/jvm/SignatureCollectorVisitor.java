package com.github.kayjamlang.backend.jvm;

import com.github.kayjamlang.backend.jvm.types.ClassJVMType;
import com.github.kayjamlang.backend.jvm.types.data.ConstInfo;
import com.github.kayjamlang.backend.jvm.types.data.FunctionInfo;
import com.github.kayjamlang.backend.jvm.types.data.PropertyInfo;
import com.github.kayjamlang.backend.jvm.utils.NamesUtils;
import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Constant;
import com.github.kayjamlang.core.expressions.data.Property;
import com.github.kayjamlang.core.statements.ClassStatement;
import com.github.kayjamlang.core.statements.FunctionStatement;
import com.github.kayjamlang.core.statements.ObjectStatement;

import java.util.List;
import java.util.stream.Collectors;

public class SignatureCollectorVisitor {
    private final SymbolRegistry symbolRegistry;
    private ClassJVMType currentClass = null;
    private KayJamFile currentFileContext = null;

    public SignatureCollectorVisitor(SymbolRegistry symbolRegistry) {
        this.symbolRegistry = symbolRegistry;
    }

    public void visitKayJamFile(KayJamFile file) {
        this.currentFileContext = file;

        for (ClassStatement classNode : file.classes) {
            visitClassStatement(classNode);
        }

        // TODO: Обработать глобальные функции, константы и переменные, если они есть на уровне файла
        // ВАЖНО: Если есть глобальные переменные/константы, их типы также нужно будет разрешать
        // с использованием currentFileContext.

        this.currentFileContext = null;
    }

    public void visitClassStatement(ClassStatement classNode) {
        if (currentFileContext == null) {
            throw new IllegalStateException("ClassStatement visited without KayJamFile context.");
        }

        String classFQN = NamesUtils.getFQNFromNamespaceAndSimpleName(currentFileContext.namespace, classNode.name);
        ClassJVMType classInfo = new ClassJVMType(
                classFQN,
                classNode instanceof ObjectStatement ?
                        ClassJVMType.ClassType.OBJECT:
                        ClassJVMType.ClassType.CLASS
        );
        classInfo.fileContext = currentFileContext;

        if (classNode.parentClasses != null && !classNode.parentClasses.isEmpty()) {
            classInfo.parentTypes.addAll(classNode.parentClasses);
        }

        this.symbolRegistry.registerClass(classInfo);
        this.currentClass = classInfo;

        for (Constant constant : classNode.constants) {
            visitConstant(constant);
        }

        for (Property property : classNode.properties) {
            visitProperty(property);
        }

        for (FunctionStatement function : classNode.functions) {
            visitFunctionStatement(function);
        }

        this.currentClass = null;
    }

    public void visitConstant(Constant constant) {
        if (currentClass == null) {
            throw new IllegalStateException("Constant out class!");
        }

        ConstInfo constInfo = new ConstInfo(
                constant.name, "any", constant.accessType
        );

        currentClass.constants.put(constInfo.name, constInfo);
    }


    public void visitProperty(Property property) {
        if (currentClass == null) {
            throw new IllegalStateException("Property out class!");
        }

        PropertyInfo propertyInfo = new PropertyInfo(
                property.name, null, property.accessType, property
        );

        currentClass.properties.put(propertyInfo.name, propertyInfo);
    }

    public void visitFunctionStatement(FunctionStatement funcNode) {
        if (currentClass == null) {
            throw new IllegalStateException("Method out class!");
        }

        List<Type> arguments = funcNode.arguments.stream()
                .map(it -> it.type)
                .collect(Collectors.toList());

        FunctionInfo methodInfo = new FunctionInfo(funcNode.name, arguments, funcNode.returnType, funcNode.accessType);
        currentClass.functions.put(methodInfo.name, methodInfo);
    }
}