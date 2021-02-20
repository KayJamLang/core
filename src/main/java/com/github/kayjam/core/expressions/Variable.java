package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.Type;
import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.opcodes.AccessIdentifier;
import com.github.kayjam.core.provider.Context;

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

