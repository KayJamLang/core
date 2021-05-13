package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class AssertNullExpression extends Expression {

    public final Expression expression;

    public AssertNullExpression(Expression expression, int line) {
        super(AccessType.NONE, line);

        this.expression = expression;
    }
}
