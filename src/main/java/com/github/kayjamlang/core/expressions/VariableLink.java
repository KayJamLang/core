package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class VariableLink extends Expression {
    public final String name;

    public VariableLink(String name, int line) {
        super(AccessIdentifier.NONE, line);
        this.name = name;
    }

    @Override
    public String toString() {
        return "VariableLink{" +
                "name='" + name + '\'' +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}
