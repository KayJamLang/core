package com.github.kayjamlang.backend.jvm;

import com.github.kayjamlang.backend.jvm.types.*;
import com.github.kayjamlang.backend.tree.KayJamExpressionVisitor;
import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.FunctionContainer;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.expressions.data.Operation;
import com.github.kayjamlang.core.expressions.loops.ForExpression;
import com.github.kayjamlang.core.expressions.loops.WhileExpression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

public class JVMKayJamExpressionVisitor extends KayJamExpressionVisitor<Object> {
    private static final TypeKayJamExpressionVisitor typeVisitor = new TypeKayJamExpressionVisitor();
    private static String snakeToCamel(String str) {
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '_') {
                builder.deleteCharAt(i);
                builder.replace(
                        i, i + 1,
                        String.valueOf(
                                Character.toUpperCase(
                                        builder.charAt(i))));
            }
        }

        return builder.toString();
    }

    private String normalizeNamespace(String namespace) {
        String[] parts = namespace.split("\\\\");
        StringBuilder result = new StringBuilder();
        for (String part : parts){
            if(part.isEmpty()){
                continue;
            }

            String newPart = snakeToCamel(part);
            result.append(newPart.substring(0, 1).toLowerCase())
                    .append(newPart.substring(1))
                    .append("/");
        }

        return result.substring(0, result.length() - 1);
    }

    private String normalizeClassName(String classname) {
        return classname.substring(0, 1).toUpperCase() + classname.substring(1);
    }

    private final ArrayDeque<ClassWriter> classWriters = new ArrayDeque<>();
    private final HashMap<String, ClassWriter> finalClassWriters = new HashMap<>();
    private MethodVisitor currentMethodVisitor = null;

    @Override
    public Object visitKayJamFile(KayJamFile file) {
        String packageName = normalizeNamespace(file.namespace);
        ClassWriter fileClassWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        String fileName = packageName + "/" + normalizeClassName(file.getFileName()) + "KJ";
        fileClassWriter.visit(V1_8,
                ACC_PUBLIC,
                fileName,
                null,
                "java/lang/Object",
                new String[]{}
        );

        for (Map.Entry<String, ClassContainer> entry : file.classes.entrySet()) {
            ClassContainer classContainer = entry.getValue();
            String className = packageName + "/" + normalizeClassName(classContainer.name);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            classWriter.visit(
                    V1_8,
                    ACC_PUBLIC,
                    className,
                    null,
                    "java/lang/Object",
                    new String[]{}
            );

            if(classContainer instanceof ObjectContainer) {
                ObjectContainer objectContainer = (ObjectContainer) classContainer;
                FieldVisitor fv = classWriter.visitField(
                        ACC_STATIC + ACC_FINAL + ACC_PUBLIC,
                        "INSTANCE",
                        "L"+className+";",
                        null,
                        null);
                fv.visitEnd();

                for (Map.Entry<String, Expression> constant: objectContainer.constants.entrySet()){
                    JVMType fieldType = typeVisitor.visit(constant.getValue());
                    classWriter.visitField(
                            ACC_STATIC + ACC_FINAL + ACC_PUBLIC,
                            constant.getKey(),
                            fieldType.getDescriptor(),
                            null,
                            null).visitEnd();
                }

                MethodVisitor staticVisitor = classWriter.visitMethod(
                        ACC_STATIC, "<clinit>", "()V", null, null
                );

                staticVisitor.visitCode();
                staticVisitor.visitTypeInsn(NEW, className);
                staticVisitor.visitInsn(DUP);
                staticVisitor.visitMethodInsn(INVOKESPECIAL, className, "<init>", "()V", false);
                staticVisitor.visitFieldInsn(PUTSTATIC, className, "INSTANCE", "L"+className+";");

                currentMethodVisitor = staticVisitor;
                for (Map.Entry<String, Expression> constant: objectContainer.constants.entrySet()){
                    JVMType fieldType = typeVisitor.visit(constant.getValue());
                    visit(constant.getValue());
                    staticVisitor.visitFieldInsn(PUTSTATIC, className, constant.getKey(), fieldType.getDescriptor());
                }

                staticVisitor.visitEnd();
            }

            finalClassWriters.put(className, classWriter);
        }

        finalClassWriters.put(fileName, fileClassWriter);
        return null;
    }

    @Override
    public Object visitAccessExpression(AccessExpression expression) {
        return null;
    }

    @Override
    public Object visitArrayExpression(ArrayExpression expression) {
        return null;
    }

    @Override
    public Object visitAssertNullExpression(AssertNullExpression expression) {
        return null;
    }

    @Override
    public Object visitCallOrCreateExpression(CallOrCreateExpression expression) {
        return null;
    }

    @Override
    public Object visitCastExpression(CastExpression expression) {
        return null;
    }

    @Override
    public Object visitCompanionAccessExpression(CompanionAccessExpression expression) {
        return null;
    }

    @Override
    public Object visitFunctionRefExpression(FunctionRefExpression expression) {
        return null;
    }

    @Override
    public Object visitGetExpression(GetExpression expression) {
        return null;
    }

    @Override
    public Object visitIfExpression(IfExpression expression) {
        return null;
    }

    @Override
    public Object visitIsExpression(IsExpression expression) {
        return null;
    }

    @Override
    public Object visitNegationExpression(NegationExpression expression) {
        return null;
    }

    @Override
    public Object visitBitwiseComplementExpression(BitwiseComplementExpression expression) {
        return null;
    }

    @Override
    public Object visitOperationExpression(OperationExpression expression) {
        JVMType resultType = typeVisitor.visitOperationExpression(expression);
        if(resultType instanceof StringJVMType && expression.operation == Operation.PLUS) {

        }else {
            visit(expression.left);
            visit(expression.right);
            typeVisitor.visit(expression.left)
                    .visitOperation(currentMethodVisitor, expression.operation, typeVisitor.visit(expression.right));
        }

        return null;
    }

    @Override
    public Object visitReturnExpression(ReturnExpression expression) {
        return null;
    }

    @Override
    public Object visitValueExpression(ValueExpression expression) {
        if (expression.value instanceof String) {
            currentMethodVisitor.visitLdcInsn(expression.value.toString());
        } else if (expression.value instanceof Integer) {
            int value = (int) expression.value;
            if (value < 6) {
                currentMethodVisitor.visitInsn(ICONST_0 + value);
            } else if (value <= Byte.MAX_VALUE) {
                currentMethodVisitor.visitIntInsn(BIPUSH, value);
            } else if (value <= Short.MAX_VALUE) {
                currentMethodVisitor.visitIntInsn(SIPUSH, value);
            } else {
                currentMethodVisitor.visitLdcInsn(value);
            }
        } else if (expression.value instanceof Long) {
            long value = (long) expression.value;
            if (value <= 1) {
                currentMethodVisitor.visitInsn((int) (LCONST_0 + value));
            } else {
                currentMethodVisitor.visitLdcInsn(value);
            }
        } else if (expression.value instanceof Double) {
            long value = (long) expression.value;
            if (value <= 1) {
                currentMethodVisitor.visitInsn((int) (DCONST_0 + value));
            } else {
                currentMethodVisitor.visitLdcInsn(value);
            }
        }else if (expression.value instanceof Boolean) {
            currentMethodVisitor.visitLdcInsn(expression.value);
        }

        return null;
    }

    @Override
    public Object visitVariableExpression(VariableExpression expression) {
        return null;
    }

    @Override
    public Object visitVariableLinkExpression(VariableLinkExpression expression) {
        return null;
    }

    @Override
    public Object visitForExpression(ForExpression expression) {
        return null;
    }

    @Override
    public Object visitWhileExpression(WhileExpression expression) {
        return null;
    }

    @Override
    public Object visitFunctionContainer(FunctionContainer expression) {
        return null;
    }

    public HashMap<String, ClassWriter> getClasses() {
        return finalClassWriters;
    }
}
