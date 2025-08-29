package com.github.kayjamlang.backend.jvm.generators;

import com.github.kayjamlang.backend.jvm.SymbolRegistry;
import com.github.kayjamlang.backend.jvm.TypeKayJamStatementVisitor;
import com.github.kayjamlang.backend.jvm.types.ClassJVMType;
import com.github.kayjamlang.backend.jvm.types.JVMType;
import com.github.kayjamlang.backend.jvm.utils.AccessUtils;
import com.github.kayjamlang.backend.jvm.utils.NamesUtils;
import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.expressions.data.Constant;
import com.github.kayjamlang.core.expressions.data.Property;
import com.github.kayjamlang.core.statements.ClassStatement;
import com.github.kayjamlang.core.statements.FunctionStatement;
import com.github.kayjamlang.core.statements.ObjectStatement;
import com.github.kayjamlang.core.statements.Statement;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class ClassGenerator {
    private final KayJamFile contextFile;
    private final ClassStatement classStatement;
    private final String packageName;
    private final SymbolRegistry symbolRegistry;
    private final TypeKayJamStatementVisitor typeVisitor;
    private final String classFQN;
    private final ClassJVMType signatureType;

    public ClassGenerator(
            KayJamFile contextFile,
            ClassStatement classStatement,
            String packageName,
            SymbolRegistry symbolRegistry
    ) {
        this.contextFile = contextFile;
        this.classStatement = classStatement;
        this.packageName = packageName;
        this.symbolRegistry = symbolRegistry;
        this.classFQN = NamesUtils.getFQNFromNamespaceAndSimpleName(contextFile.namespace, classStatement.name);
        this.signatureType = symbolRegistry.findClass(classFQN);
        this.typeVisitor = new TypeKayJamStatementVisitor(symbolRegistry, signatureType);
    }

    public ClassWriter generate() {


        String className = packageName + "/" + classStatement.name;
        String superClass = signatureType.superType == null ? "java/lang/Object" : signatureType.superType.getDescriptor();
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_8, AccessUtils.accessIntByAccessType(classStatement.accessType),
                className, null, superClass, null);

        for (Constant constant : classStatement.constants) {
            int accessOpcode = ACC_STATIC | ACC_FINAL | AccessUtils.accessIntByAccessType(constant.accessType);
            JVMType fieldType = typeVisitor.visit(constant.value);
            cw.visitField(
                    accessOpcode,
                    constant.name,
                    fieldType.getDescriptor(),
                    null,
                    null).visitEnd();
        }

        boolean isStatic = classStatement instanceof ObjectStatement;
        for (Property property : classStatement.properties) {
            int accessOpcode = (isStatic ? ACC_STATIC : 0) | AccessUtils.accessIntByAccessType(property.accessType);
            JVMType fieldType = typeVisitor.visit(property.value);
            cw.visitField(
                    accessOpcode,
                    property.name,
                    fieldType.getDescriptor(),
                    null,
                    null).visitEnd();
        }

        if (!classStatement.constants.isEmpty() || (isStatic && !classStatement.properties.isEmpty())) {
            MethodVisitor clinitVisitor = cw.visitMethod(ACC_STATIC, "<clinit>", "()V",
                    null, null);
            clinitVisitor.visitCode();

            MethodBodyGenerator bodyGenerator = new MethodBodyGenerator(clinitVisitor, typeVisitor);
            for (Constant constant : classStatement.constants) {
                JVMType fieldType = typeVisitor.visit(constant.value);
                bodyGenerator.visit(constant.value);
                clinitVisitor.visitFieldInsn(PUTSTATIC, className, constant.name, fieldType.getDescriptor());
                bodyGenerator.popStack(1);
            }

            if (isStatic) {
                for (Property property : classStatement.properties) {
                    JVMType fieldType = typeVisitor.visit(property.value);
                    bodyGenerator.visit(property.value);
                    clinitVisitor.visitFieldInsn(PUTSTATIC, className, property.name, fieldType.getDescriptor());
                    bodyGenerator.popStack(1);
                }
            }

            clinitVisitor.visitInsn(RETURN);
            clinitVisitor.visitMaxs(bodyGenerator.getMaxStack(), 0);
            clinitVisitor.visitEnd();
        }

//        // Генерация конструктора
//        generateDefaultConstructor(cw, superClass);

        // Особая логика для ObjectStatement
        if (classStatement instanceof ObjectStatement) {
            generateObjectInstance(cw, className, (ObjectStatement) classStatement);
        }

//        // Генерация других методов (цикл по classStatement.functions)
        for (FunctionStatement func : classStatement.functions) {
            MethodVisitor clinitVisitor = cw.visitMethod(
                    (isStatic ? ACC_STATIC : 0) | AccessUtils.accessIntByAccessType(func.accessType),
                    func.name,
                    "()" + symbolRegistry.resolveType(func.returnType, contextFile).getDescriptor(),
                    null, null);
            clinitVisitor.visitCode();

            MethodBodyGenerator bodyGenerator = new MethodBodyGenerator(clinitVisitor, typeVisitor);
            for (Statement statement : func.children) {
                bodyGenerator.visit(statement);
            }

            clinitVisitor.visitMaxs(bodyGenerator.getMaxStack(), 0);
            clinitVisitor.visitEnd();
        }

        cw.visitEnd();
        return cw;
    }

    private void generateObjectInstance(ClassWriter cw, String className, ObjectStatement objectStatement) {

    }

    // ... другие приватные методы ...
}