package com.github.kayjamlang.core.expressions.loops;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.opcodes.AccessType;

/**
 * 'for' loop expression
 */
public class ForExpression extends Expression {
    /**
     * Name of variable for change value
     */
    public final String variableName;

    /**
     * Range from expression
     */
    public final Expression range;

    /**
     * For loop body
     */
    public final Expression body;

    /**
     *
     * @param variableName Name of variable for change value
     * @param range Range from expression
     * @param body For loop body
     * @param line Line for start
     */
    public ForExpression(String variableName, Expression range, Expression body, int line) {
        super(AccessType.NONE, line);
        this.variableName = variableName;
        this.range = range;
        this.body = body;
    }

    @Override
    public boolean isConstant() {
        return false;
    }
}
