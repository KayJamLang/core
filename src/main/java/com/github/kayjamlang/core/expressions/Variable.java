package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class Variable extends Expression {
    public final String name;
    public final Expression expression;

    public Variable(String name, Expression expression, AccessIdentifier identifier, int line) {
        super(identifier, line);
        this.name = name;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "name='" + name + '\'' +
                ", expression=" + expression +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}

