package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class NegationExpression extends Expression {
    /**
     * Expression for negation
     */
    public final Expression expression;

    /**
     * @param expression Expression for negation
     * @param line Start line
     */
    public NegationExpression(Expression expression, int line) {
        super(AccessType.NONE, line);
        this.expression = expression;
    }

    @Override
    public boolean isConstant() {
        return expression.isConstant();
    }
}
