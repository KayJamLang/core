package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class NegationExpression extends Expression {

    public final Expression expression;

    public NegationExpression(Expression expression, int line) {
        super(AccessIdentifier.NONE, line);
        this.expression = expression;
    }
}
