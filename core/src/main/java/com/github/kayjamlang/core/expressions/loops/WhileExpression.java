package com.github.kayjamlang.core.expressions.loops;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.opcodes.AccessType;

public class WhileExpression extends Expression {

    /**
     * Condition for continue loop
     */
    public final Expression condition;

    /**
     * Loop body
     */
    public final Expression expression;

    /**
     * Creates loop
     * @param condition Condition for continue loop
     * @param expression Loop body
     * @param line Line of start
     */
    public WhileExpression(Expression condition, Expression expression, int line) {
        super(AccessType.NONE, line);
        this.condition = condition;
        this.expression = expression;
    }

    @Override
    public boolean isConstant() {
        return false;
    }
}
