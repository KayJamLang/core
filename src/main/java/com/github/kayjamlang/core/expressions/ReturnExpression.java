package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class ReturnExpression extends Expression {

    /**
     * Return value
     * Can be null, if return void value
     */
    public final Expression expression;

    public ReturnExpression(Expression expression, int line) {
        super(AccessType.NONE, line);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Return{" +
                "expression=" + expression +
                ", identifier=" + accessType +
                ", line=" + line +
                '}';
    }
}