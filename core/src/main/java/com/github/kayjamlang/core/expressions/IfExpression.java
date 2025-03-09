package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class IfExpression extends Expression {
    /**
     * Condition for if
     */
    public final Expression condition;

    /**
     * Body for true condition
     */
    public final Expression ifTrue;

    /**
     * Body for false condition
     */
    public final Expression ifFalse;

    /**
     * @param condition Condition for if
     * @param ifTrue    Body for true condition
     * @param ifFalse   Body for false condition
     * @param line      Start line
     */
    public IfExpression(Expression condition, Expression ifTrue, Expression ifFalse, int line) {
        super(AccessType.NONE, line);

        this.condition = condition;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    @Override
    public boolean isConstant() {
        return condition.isConstant() && ifTrue.isConstant() && ifFalse.isConstant();
    }
}