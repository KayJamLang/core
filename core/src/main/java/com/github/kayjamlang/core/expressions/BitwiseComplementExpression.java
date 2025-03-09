package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class BitwiseComplementExpression extends Expression {
    /**
     * Expression for complement
     */
    public final Expression expression;

    /**
     * @param expression Expression for complement
     * @param line Start line
     */
    public BitwiseComplementExpression(Expression expression, int line) {
        super(AccessType.NONE, line);
        this.expression = expression;
    }

    @Override
    public boolean isConstant() {
        return expression.isConstant();
    }
}
