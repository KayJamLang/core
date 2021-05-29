package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.HashMap;
import java.util.Map;

public abstract class Expression implements Cloneable {
    /**
     * Type of access
     */
    public final AccessType accessType;

    /**
     * Specific data for provider
     */
    public Map<String, Object> data = new HashMap<>(); // это был fix java:S1319 а не fix java:S5993

    /**
     * Expression line
     */
    public final int line;

    /**
     * Creates Expression
     * @param accessType Type of access
     * @param line Expression line
     */
    protected Expression(AccessType accessType, int line) {
        this.accessType = accessType;
        this.line = line;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Expression clone() throws CloneNotSupportedException {
        Expression expression = (Expression) super.clone();
        expression.data = (Map<String, Object>) ((HashMap<String, Object>) data).clone();
        return expression;
    }
}