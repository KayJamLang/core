package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.Type;
import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.opcodes.AccessIdentifier;
import com.github.kayjam.core.provider.Context;

public class Const extends Expression {

    public final Object value;

    public Const(Object value, int line) {
        super(AccessIdentifier.NONE, line);
        this.value = value;
    }

    @Override
    public String toString() {
        return "Const{" +
                "value=" + value +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}