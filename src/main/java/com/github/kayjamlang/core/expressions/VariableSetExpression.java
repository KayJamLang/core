package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class VariableSetExpression extends VariableExpression {

    /**
     * @param name Variable name
     * @param expression Set value
     * @param line Start line
     */
    public VariableSetExpression(String name, Expression expression, int line) {
        super(name, expression, AccessType.NONE, line);
    }
}