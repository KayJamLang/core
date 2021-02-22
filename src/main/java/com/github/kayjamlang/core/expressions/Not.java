package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class Not extends Expression {

    public final Expression expression;

    public Not(Expression expression, int line) {
        super(AccessIdentifier.NONE, line);
        this.expression = expression;
    }
}
