package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class CompanionAccessExpression extends Expression {

    public final String className;
    public final Expression child;

    public CompanionAccessExpression(String className, Expression child, int line) {
        super(AccessType.NONE, line);
        this.className = className;
        this.child = child;
    }
}