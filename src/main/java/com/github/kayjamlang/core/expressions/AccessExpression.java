package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class AccessExpression extends Expression {
    public final Expression root;
    public final Expression child;

    public AccessExpression(Expression root, Expression child, int line) {
        super(AccessIdentifier.NONE, line);
        this.root = root;
        this.child = child;
    }

    @Override
    public String toString() {
        return "Access{" +
                "root=" + root +
                ", child=" + child +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}