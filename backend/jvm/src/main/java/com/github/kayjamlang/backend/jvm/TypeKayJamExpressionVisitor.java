package com.github.kayjamlang.backend.jvm;

import com.github.kayjamlang.backend.jvm.types.*;
import com.github.kayjamlang.backend.tree.KayJamExpressionVisitor;
import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.FunctionContainer;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.expressions.loops.ForExpression;
import com.github.kayjamlang.core.expressions.loops.WhileExpression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

public class TypeKayJamExpressionVisitor extends KayJamExpressionVisitor<JVMType> {
    @Override
    public JVMType visitKayJamFile(KayJamFile file) {
        return null;
    }

    @Override
    public JVMType visitAccessExpression(AccessExpression expression) {
        return null;
    }

    @Override
    public JVMType visitArrayExpression(ArrayExpression expression) {
        return null;
    }

    @Override
    public JVMType visitAssertNullExpression(AssertNullExpression expression) {
        return null;
    }

    @Override
    public JVMType visitCallOrCreateExpression(CallOrCreateExpression expression) {
        return null;
    }

    @Override
    public JVMType visitCastExpression(CastExpression expression) {
        return null;
    }

    @Override
    public JVMType visitCompanionAccessExpression(CompanionAccessExpression expression) {
        return null;
    }

    @Override
    public JVMType visitFunctionRefExpression(FunctionRefExpression expression) {
        return null;
    }

    @Override
    public JVMType visitGetExpression(GetExpression expression) {
        return null;
    }

    @Override
    public JVMType visitIfExpression(IfExpression expression) {
        return null;
    }

    @Override
    public JVMType visitIsExpression(IsExpression expression) {
        return null;
    }

    @Override
    public JVMType visitNegationExpression(NegationExpression expression) {
        return null;
    }

    @Override
    public JVMType visitBitwiseComplementExpression(BitwiseComplementExpression expression) {
        return null;
    }

    @Override
    public JVMType visitOperationExpression(OperationExpression expression) {
        return visit(expression.left).getTypeAfterOperation(expression.operation, visit(expression.right));
    }

    @Override
    public JVMType visitReturnExpression(ReturnExpression expression) {
        return null;
    }

    @Override
    public JVMType visitValueExpression(ValueExpression expression) {
        if (expression.value instanceof String) {
            return StringJVMType.INSTANCE;
        } else if (expression.value instanceof Integer) {
            return IntJVMType.INT;
        } else if (expression.value instanceof Long) {
            return LongJVMType.INSTANCE;
        } else if (expression.value instanceof Double) {
            return DoubleJVMType.INSTANCE;
        }else if (expression.value instanceof BooleanJVMType) {
            return BooleanJVMType.INSTANCE;
        }

        return AnyJVMType.INSTANCE;
    }

    @Override
    public JVMType visitVariableExpression(VariableExpression expression) {
        return null;
    }

    @Override
    public JVMType visitVariableLinkExpression(VariableLinkExpression expression) {
        return null;
    }

    @Override
    public JVMType visitForExpression(ForExpression expression) {
        return null;
    }

    @Override
    public JVMType visitWhileExpression(WhileExpression expression) {
        return null;
    }

    @Override
    public JVMType visitFunctionContainer(FunctionContainer expression) {
        return null;
    }
}
