package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.containers.Container;

import static com.github.kayjam.core.opcodes.AccessIdentifier.NONE;

public class Not extends Expression {

    public final Expression expression;

    public Not(Expression expression, int line) {
        super(NONE, line);
        this.expression = expression;
    }

    @Override
    public Object execute(Container parent, Container varParent) throws Exception {
        Object response = expression.execute(parent, varParent);
        if(response instanceof Boolean)
            return !(Boolean) response;

        throw new RuntimeException("Expression returns unknown type value");
    }
}
