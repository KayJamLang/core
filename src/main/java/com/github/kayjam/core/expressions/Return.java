package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.opcodes.AccessIdentifier;

public class Return extends Expression{
    public final Expression expression;

    public Return(Expression expression, int line) {
        super(AccessIdentifier.NONE, line);
        this.expression = expression;
    }

    @Override
    public Object execute(Container parent, Container argsParent) throws Exception {
        return expression.execute(parent, argsParent);
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