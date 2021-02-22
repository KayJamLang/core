package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class If extends Expression {
    public final Expression condition;
    public final Expression ifTrue;
    public final Expression ifFalse;

    public If(Expression condition, Expression ifTrue, Expression ifFalse, int line) {
        super(AccessIdentifier.NONE, line);

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
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}