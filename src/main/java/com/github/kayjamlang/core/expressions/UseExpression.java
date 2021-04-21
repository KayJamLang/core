package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class UseExpression extends Expression {
    public final Expression expression;

    public UseExpression(Expression expression, int line) {
        super(AccessIdentifier.NONE, line);
        this.expression = expression;
    }

    public interface UseInterface{
        Expression getExpression(String path) throws Exception;
    }
}