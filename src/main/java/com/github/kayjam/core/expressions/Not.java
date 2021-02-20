package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.Type;
import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.provider.Context;

import static com.github.kayjam.core.opcodes.AccessIdentifier.NONE;

public class Not extends Expression {

    public final Expression expression;

    public Not(Expression expression, int line) {
        super(NONE, line);
        this.expression = expression;
    }
}
