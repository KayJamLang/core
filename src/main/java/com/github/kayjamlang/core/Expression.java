package com.github.kayjamlang.core;

import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.HashMap;
import java.util.Map;

public abstract class Expression implements Cloneable {
    public final AccessIdentifier identifier;
    public HashMap<String, Object> data = new HashMap<>();
    public final int line;

    public Expression(AccessIdentifier identifier, int line) {
        this.identifier = identifier;
        this.line = line;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "identifier=" + identifier +
                ", line=" + line +
                '}';
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Expression clone() throws CloneNotSupportedException {
        Expression expression = (Expression) super.clone();
        expression.data = (HashMap<String, Object>) data.clone();
        return expression;
    }
}