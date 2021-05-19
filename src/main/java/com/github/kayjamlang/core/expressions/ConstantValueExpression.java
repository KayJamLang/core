package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class ConstantValueExpression extends Expression {

    public final String name;
    public final ValueExpression value;

    /**
     * Creates Constant Expression
     *
     * @param name Constant name
     * @param value Constant value
     * @param line Expression line
     */
    public ConstantValueExpression(String name, ValueExpression value, int line) {
        super(AccessType.NONE, line);
        this.name = name;
        this.value = value;
    }
}
