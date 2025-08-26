package com.github.kayjamlang.core.expressions;

public class VariableSetExpression extends VariableExpression {

    /**
     * @param name Variable name
     * @param expression Set value
     * @param line Start line
     */
    public VariableSetExpression(String name, Expression expression, int line) {
        super(name, expression, line);
    }
}