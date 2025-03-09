package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class VariableLinkExpression extends Expression {
    /**
     * Variable name for get
     */
    public final String name;

    /**
     * @param name Variable name for get
     * @param line Start line
     */
    public VariableLinkExpression(String name, int line) {
        super(AccessType.NONE, line);
        this.name = name;
    }

    @Override
    public boolean isConstant() {
        return false;
    }
}
