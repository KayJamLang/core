package com.github.kayjamlang.backend.jvm;

import com.github.kayjamlang.backend.jvm.types.*;
import com.github.kayjamlang.backend.tree.KayJamStatementVisitor;
import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.expressions.loops.ForExpression;
import com.github.kayjamlang.core.expressions.loops.WhileExpression;

public class TypeKayJamStatementVisitor extends KayJamStatementVisitor<JVMType> {
    private final SymbolRegistry symbolRegistry;
    private final ClassJVMType currentSignature;

    public TypeKayJamStatementVisitor(
            SymbolRegistry symbolRegistry,
            ClassJVMType currentSignature
    ) {
        this.symbolRegistry = symbolRegistry;
        this.currentSignature = currentSignature;
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
            return IntJVMType.INSTANCE;
        } else if (expression.value instanceof Long) {
            return LongJVMType.INSTANCE;
        } else if (expression.value instanceof Double) {
            return DoubleJVMType.INSTANCE;
        } else if (expression.value instanceof Boolean) {
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
}
