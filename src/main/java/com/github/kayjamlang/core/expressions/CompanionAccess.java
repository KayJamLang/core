package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class CompanionAccess extends Expression {

    public final String className;
    public final Expression child;

    public CompanionAccess(String className, Expression child, int line) {
        super(AccessIdentifier.NONE, line);
        this.className = className;
        this.child = child;
    }
}