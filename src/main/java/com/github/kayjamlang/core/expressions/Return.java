package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class Return extends Expression {
    public final Expression expression;

    public Return(Expression expression, int line) {
        super(AccessIdentifier.NONE, line);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Return{" +
                "expression=" + expression +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}