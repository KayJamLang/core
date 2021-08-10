package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class AccessExpression extends Expression {
    /**
     * Root expression returns class
     */
    public final Expression root;

    /**
     * Inner expression
     */
    public final Expression child;

    /**
     * @param root Root expression returns class
     * @param child Inner expression
     * @param line Start line
     */
    public AccessExpression(Expression root, Expression child, int line) {
        super(AccessType.NONE, line);
        this.root = root;
        this.child = child;
    }
}