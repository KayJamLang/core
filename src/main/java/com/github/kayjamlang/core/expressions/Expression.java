package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.HashMap;

public abstract class Expression implements Cloneable {
    /**
     * Type of access
     */
    public final AccessType accessType;

    /**
     * Specific data for provider
     */
    public HashMap<String, Object> data = new HashMap<>();

    /**
     * Expression line
     */
    public final int line;

    /**
     * Creates Expression
     * @param accessType Type of access
     * @param line Expression line
     */
    public Expression(AccessType accessType, int line) {
        this.accessType = accessType;
        this.line = line;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Expression clone() throws CloneNotSupportedException {
        Expression expression = (Expression) super.clone();
        expression.data = (HashMap<String, Object>) data.clone();
        return expression;
    }
}