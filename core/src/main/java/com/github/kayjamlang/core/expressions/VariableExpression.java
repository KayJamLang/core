package com.github.kayjamlang.core.expressions;

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
     * @param line Start line
     */
    public VariableExpression(String name, Expression expression, int line) {
        super(line);
        this.name = name;
        this.expression = expression;
    }

    @Override
    public boolean isConstant() {
        return false;
    }
}

