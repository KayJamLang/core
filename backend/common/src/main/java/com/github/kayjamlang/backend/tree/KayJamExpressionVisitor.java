package com.github.kayjamlang.backend.tree;

import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.containers.FunctionContainer;
import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.expressions.loops.*;

public abstract class KayJamExpressionVisitor<T> {
    public T visit(Expression expression) {
        if(expression instanceof AccessExpression) {
            return visitAccessExpression((AccessExpression) expression);
        }else if(expression instanceof ArrayExpression) {
            return visitArrayExpression((ArrayExpression) expression);
        }else if(expression instanceof AssertNullExpression) {
            return visitAssertNullExpression((AssertNullExpression) expression);
        }else if(expression instanceof CallOrCreateExpression) {
            return visitCallOrCreateExpression((CallOrCreateExpression) expression);
        }else if(expression instanceof CastExpression) {
            return visitCastExpression((CastExpression) expression);
        }else if(expression instanceof CompanionAccessExpression) {
            return visitCompanionAccessExpression((CompanionAccessExpression) expression);
        }else if(expression instanceof FunctionRefExpression) {
            return visitFunctionRefExpression((FunctionRefExpression) expression);
        }else if(expression instanceof GetExpression) {
            return visitGetExpression((GetExpression) expression);
        }else if(expression instanceof IfExpression) {
            return visitIfExpression((IfExpression) expression);
        }else if(expression instanceof IsExpression) {
            return visitIsExpression((IsExpression) expression);
        }else if(expression instanceof NegationExpression) {
            return visitNegationExpression((NegationExpression) expression);
        }else if(expression instanceof BitwiseComplementExpression) {
            return visitBitwiseComplementExpression((BitwiseComplementExpression) expression);
        }else if(expression instanceof OperationExpression) {
            return visitOperationExpression((OperationExpression) expression);
        }else if(expression instanceof ReturnExpression) {
            return visitReturnExpression((ReturnExpression) expression);
        }else if(expression instanceof ValueExpression) {
            return visitValueExpression((ValueExpression) expression);
        }else if(expression instanceof VariableExpression) {
            return visitVariableExpression((VariableExpression) expression);
        }else if(expression instanceof VariableLinkExpression) {
            return visitVariableLinkExpression((VariableLinkExpression) expression);
        }else if(expression instanceof ForExpression) {
            return visitForExpression((ForExpression) expression);
        }else if(expression instanceof WhileExpression) {
            return visitWhileExpression((WhileExpression) expression);
        }else if(expression instanceof FunctionContainer) {
            return visitFunctionContainer((FunctionContainer) expression);
        }


        throw new IllegalStateException("Not implemented yet: "+expression.getClass().getSimpleName());
    }

    public abstract T visitKayJamFile(KayJamFile file);
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

    public abstract T visitFunctionContainer(FunctionContainer expression);
}
