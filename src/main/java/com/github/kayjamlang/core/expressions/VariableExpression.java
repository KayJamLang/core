package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class VariableExpression extends Expression {
    /**
     * Variable name
     */
    public final String name;

    /**
     * Set value
     */
    public final Expression expression;

    /**
     * @param name Variable name
     * @param expression Set value
     * @param accessType Type of access
     * @param line Start line
     */
    public VariableExpression(String name, Expression expression, AccessType accessType, int line) {
        super(accessType, line);
        this.name = name;
        this.expression = expression;
    }
}

