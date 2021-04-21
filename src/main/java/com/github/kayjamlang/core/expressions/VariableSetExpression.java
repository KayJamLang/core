package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class VariableSetExpression extends VariableExpression {

    public VariableSetExpression(String name, Expression expression, int line) {
        super(name, expression, AccessIdentifier.NONE, line);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}