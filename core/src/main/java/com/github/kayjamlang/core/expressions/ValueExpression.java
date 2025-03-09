package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class ValueExpression extends Expression {
    /**
     * Value
     */
    public final Object value;

    /**
     * @param value Value
     */
    public ValueExpression(Object value) {
        super(AccessType.NONE, -1);
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean isConstant() {
        return true;
    }
}