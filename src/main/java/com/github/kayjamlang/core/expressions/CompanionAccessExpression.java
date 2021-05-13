package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class CompanionAccessExpression extends Expression {
    /**
     * Class name with companion
     */
    public final String className;

    /**
     * Inner expression for execute in companion class
     */
    public final Expression child;

    /**
     * @param className Class name with companion
     * @param child Inner expression for execute in companion class
     * @param line Start line
     */
    public CompanionAccessExpression(String className, Expression child, int line) {
        super(AccessType.NONE, line);
        this.className = className;
        this.child = child;
    }
}