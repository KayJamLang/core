package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

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