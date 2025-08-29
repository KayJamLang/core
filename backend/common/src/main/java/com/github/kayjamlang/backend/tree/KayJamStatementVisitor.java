package com.github.kayjamlang.backend.tree;

import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.expressions.loops.*;
import com.github.kayjamlang.core.statements.Statement;

public abstract class KayJamStatementVisitor<T> {
    public T visit(Statement statement) {
        if(statement instanceof AccessExpression) {
            return visitAccessExpression((AccessExpression) statement);
        }else if(statement instanceof ArrayExpression) {
            return visitArrayExpression((ArrayExpression) statement);
        }else if(statement instanceof AssertNullExpression) {
            return visitAssertNullExpression((AssertNullExpression) statement);
        }else if(statement instanceof CallOrCreateExpression) {
            return visitCallOrCreateExpression((CallOrCreateExpression) statement);
        }else if(statement instanceof CastExpression) {
            return visitCastExpression((CastExpression) statement);
        }else if(statement instanceof CompanionAccessExpression) {
            return visitCompanionAccessExpression((CompanionAccessExpression) statement);
        }else if(statement instanceof FunctionRefExpression) {
            return visitFunctionRefExpression((FunctionRefExpression) statement);
        }else if(statement instanceof GetExpression) {
            return visitGetExpression((GetExpression) statement);
        }else if(statement instanceof IfExpression) {
            return visitIfExpression((IfExpression) statement);
        }else if(statement instanceof IsExpression) {
            return visitIsExpression((IsExpression) statement);
        }else if(statement instanceof NegationExpression) {
            return visitNegationExpression((NegationExpression) statement);
        }else if(statement instanceof BitwiseComplementExpression) {
            return visitBitwiseComplementExpression((BitwiseComplementExpression) statement);
        }else if(statement instanceof OperationExpression) {
            return visitOperationExpression((OperationExpression) statement);
        }else if(statement instanceof ReturnExpression) {
            return visitReturnExpression((ReturnExpression) statement);
        }else if(statement instanceof ValueExpression) {
            return visitValueExpression((ValueExpression) statement);
        }else if(statement instanceof VariableExpression) {
            return visitVariableExpression((VariableExpression) statement);
        }else if(statement instanceof VariableLinkExpression) {
            return visitVariableLinkExpression((VariableLinkExpression) statement);
        }else if(statement instanceof ForExpression) {
            return visitForExpression((ForExpression) statement);
        }else if(statement instanceof WhileExpression) {
            return visitWhileExpression((WhileExpression) statement);
        }


        throw new IllegalStateException("Not implemented yet: "+statement.getClass().getSimpleName());
    }

    public abstract T visitAccessExpression(AccessExpression expression);
    public abstract T visitArrayExpression(ArrayExpression expression);
    public abstract T visitAssertNullExpression(AssertNullExpression expression);
    public abstract T visitCallOrCreateExpression(CallOrCreateExpression expression);
    public abstract T visitCastExpression(CastExpression expression);
    public abstract T visitCompanionAccessExpression(CompanionAccessExpression expression);
    public abstract T visitFunctionRefExpression(FunctionRefExpression expression);
    public abstract T visitGetExpression(GetExpression expression);
    public abstract T visitIfExpression(IfExpression expression);
    public abstract T visitIsExpression(IsExpression expression);
    public abstract T visitNegationExpression(NegationExpression expression);
    public abstract T visitBitwiseComplementExpression(BitwiseComplementExpression expression);
    public abstract T visitOperationExpression(OperationExpression expression);
    public abstract T visitReturnExpression(ReturnExpression expression);
    public abstract T visitValueExpression(ValueExpression expression);
    public abstract T visitVariableExpression(VariableExpression expression);
    public abstract T visitVariableLinkExpression(VariableLinkExpression expression);
    public abstract T visitForExpression(ForExpression expression);
    public abstract T visitWhileExpression(WhileExpression expression);
}
