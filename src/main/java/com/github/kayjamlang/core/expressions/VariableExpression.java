package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class VariableExpression extends Expression {
    public final String name;
    public final Expression expression;

    public VariableExpression(String name, Expression expression, AccessType identifier, int line) {
        super(identifier, line);
        this.name = name;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "name='" + name + '\'' +
                ", expression=" + expression +
                ", identifier=" + accessType +
                ", line=" + line +
                '}';
    }
}

