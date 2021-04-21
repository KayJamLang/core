package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class AssertNullExpression extends Expression {

    public final Expression expression;

    public AssertNullExpression(Expression expression, int line) {
        super(AccessIdentifier.NONE, line);

        this.expression = expression;
    }
}
