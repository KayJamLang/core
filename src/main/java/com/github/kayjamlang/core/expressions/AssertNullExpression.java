package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class AssertNullExpression extends Expression {
    /**
     * Expression for assert null
     */
    public final Expression expression;

    /**
     * @param expression Expression for assert null
     * @param line Start line
     */
    public AssertNullExpression(Expression expression, int line) {
        super(AccessType.NONE, line);

        this.expression = expression;
    }
}
