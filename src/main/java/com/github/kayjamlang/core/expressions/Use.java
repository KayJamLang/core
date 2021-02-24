package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class Use extends Expression {
    public final Expression expression;

    public Use(Expression expression, int line) {
        super(AccessIdentifier.NONE, line);
        this.expression = expression;
    }

    public interface UseInterface{
        Expression getExpression(String path) throws Exception;
    }
}