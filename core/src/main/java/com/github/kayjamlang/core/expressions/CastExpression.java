package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.opcodes.AccessType;

public class CastExpression extends Expression {
    /**
     * Expression for cast
     */
    public final Expression expression;

    /**
     * Need type for cast
     */
    public final Type castType;

    /**
     *
     * @param expression Expression for cast
     * @param castType Need type for cast
     * @param line Start line
     */
    public CastExpression(Expression expression, Type castType, int line) {
        super(AccessType.NONE, line);
        this.expression = expression;
        this.castType = castType;
    }

    @Override
    public String toString() {
        return expression + " as " + castType;
    }

    @Override
    public boolean isConstant() {
        return false;
    }
}
