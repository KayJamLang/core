package com.github.kayjamlang.backend.jvm.generators;

import com.github.kayjamlang.backend.jvm.TypeKayJamExpressionVisitor;
import com.github.kayjamlang.backend.jvm.types.JVMType;
import com.github.kayjamlang.backend.jvm.utils.AccessUtils;
import com.github.kayjamlang.backend.jvm.utils.JVMNameUtils;
import com.github.kayjamlang.core.expressions.data.Constant;
import com.github.kayjamlang.core.expressions.data.Property;
import com.github.kayjamlang.core.statements.ClassStatement;
import com.github.kayjamlang.core.statements.FunctionStatement;
import com.github.kayjamlang.core.statements.ObjectStatement;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

// Новый класс
public class ClassGenerator {
    private final ClassStatement classStatement;
    private final String packageName;
    private final TypeKayJamExpressionVisitor typeVisitor = new TypeKayJamExpressionVisitor();

    public ClassGenerator(ClassStatement classStatement, String packageName) {
        this.classStatement = classStatement;
        this.packageName = packageName;
    }

    public ClassWriter generate() {
        String className = packageName + "/" + JVMNameUtils.normalizeClassName(classStatement.name);
        String superClass = "java/lang/Object"; // или из classStatement.extendsClass
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

        if(!classStatement.constants.isEmpty() || (isStatic && !classStatement.properties.isEmpty())) {
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

            if(isStatic) {
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
                    "()V",
                    null, null);
            clinitVisitor.visitCode();

            MethodBodyGenerator bodyGenerator = new MethodBodyGenerator(clinitVisitor, typeVisitor);

            clinitVisitor.visitInsn(RETURN);
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