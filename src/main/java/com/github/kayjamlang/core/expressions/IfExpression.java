package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class IfExpression extends Expression {
    public final Expression condition;
    public final Expression ifTrue;
    public final Expression ifFalse;

    public IfExpression(Expression condition, Expression ifTrue, Expression ifFalse, int line) {
        super(AccessType.NONE, line);

        this.condition = condition;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    @Override
    public String toString() {
        return "If{" +
                "condition=" + condition +
                ", ifTrue=" + ifTrue +
                ", ifFalse=" + ifFalse +
                ", identifier=" + accessType +
                ", line=" + line +
                '}';
    }
}