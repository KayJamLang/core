package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class WhileExpression extends Expression {

    public final Expression condition;
    public final Expression expression;

    public WhileExpression(Expression condition, Expression expression, int line) {
        super(AccessIdentifier.NONE, line);
        this.condition = condition;
        this.expression = expression;
    }
}
