package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.opcodes.AccessType;

public class IsExpression extends Expression {
    /**
     * Check expression
     */
    public final Expression expression;

    /**
     * Need to verify type
     */
    public final Type verifyType;

    /**
     * @param expression Check expression
     * @param verifyType Need to verify type
     * @param line Start line
     */
    public IsExpression(Expression expression, Type verifyType, int line) {
        super(AccessType.NONE, line);
        this.expression = expression;
        this.verifyType = verifyType;
    }

    @Override
    public boolean isConstant() {
        return false;
    }
}
