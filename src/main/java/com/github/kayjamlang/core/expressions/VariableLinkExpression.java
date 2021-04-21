package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class VariableLinkExpression extends Expression {
    public final String name;

    public VariableLinkExpression(String name, int line) {
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
