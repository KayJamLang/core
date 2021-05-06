package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class UseExpression extends Expression {

    public final String from;

    public UseExpression(String from, int line){
        super(AccessIdentifier.NONE, line);
        this.from = from;
        expression = null;
    }

    @Deprecated
    public final Expression expression;

    @Deprecated
    public UseExpression(Expression expression, int line) {
        super(AccessIdentifier.NONE, line);
        this.expression = expression;
        this.from = null;
    }

    @Deprecated
    public interface UseInterface{
        Expression getExpression(String path) throws Exception;
    }
}