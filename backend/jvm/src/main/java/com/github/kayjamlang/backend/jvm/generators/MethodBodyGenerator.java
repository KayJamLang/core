package com.github.kayjamlang.backend.jvm.generators;

import com.github.kayjamlang.backend.jvm.TypeKayJamExpressionVisitor;
import com.github.kayjamlang.backend.jvm.types.JVMType;
import com.github.kayjamlang.backend.jvm.types.StringJVMType;
import com.github.kayjamlang.backend.tree.KayJamExpressionVisitor;
import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.expressions.data.Operation;
import com.github.kayjamlang.core.expressions.loops.ForExpression;
import com.github.kayjamlang.core.expressions.loops.WhileExpression;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class MethodBodyGenerator extends KayJamExpressionVisitor<Object> {
    private final MethodVisitor mv;
    private final TypeKayJamExpressionVisitor typeVisitor;

    private int currentStack = 0;
    private int maxStack = 0;

    public MethodBodyGenerator(MethodVisitor mv, TypeKayJamExpressionVisitor typeVisitor) {
        this.mv = mv;
        this.typeVisitor = typeVisitor;
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
            popStack(2);
            typeVisitor.visit(expression.left)
                    .visitOperation(mv, expression.operation, typeVisitor.visit(expression.right));
            pushToStack();
        }

        return null;
    }

    @Override
    public Object visitReturnExpression(ReturnExpression expression) {
        return null;
    }

    @Override
    public Object visitValueExpression(ValueExpression expression) {
        pushToStack();
        if (expression.value instanceof String) {
            mv.visitLdcInsn(expression.value.toString());
        } else if (expression.value instanceof Integer) {
            int value = (int) expression.value;
            if (value < 6) {
                mv.visitInsn(ICONST_0 + value);
            } else if (value <= Byte.MAX_VALUE) {
                mv.visitIntInsn(BIPUSH, value);
            } else if (value <= Short.MAX_VALUE) {
                mv.visitIntInsn(SIPUSH, value);
            } else {
                mv.visitLdcInsn(value);
            }
        } else if (expression.value instanceof Long) {
            long value = (long) expression.value;
            if (value <= 1) {
                mv.visitInsn((int) (LCONST_0 + value));
            } else {
                mv.visitLdcInsn(value);
            }
        } else if (expression.value instanceof Double) {
            long value = (long) expression.value;
            if (value <= 1) {
                mv.visitInsn((int) (DCONST_0 + value));
            } else {
                mv.visitLdcInsn(value);
            }
        }else if (expression.value instanceof Boolean) {
            mv.visitLdcInsn(expression.value);
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
    
    public int getMaxStack() {
        return maxStack;
    }
    
    public void pushToStack() {
        currentStack += 1;

        if(currentStack > maxStack) {
            maxStack = currentStack;
        }
    }

    public void popStack(int stackSize) {
        currentStack -= stackSize;
    }
}