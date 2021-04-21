package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class ValueExpression extends Expression {

    public final Object value;

    public ValueExpression(Object value) {
        super(AccessIdentifier.NONE, -1);
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}