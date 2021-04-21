package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.List;

public class ArrayExpression extends Expression {

    public final List<Expression> values;

    public ArrayExpression(List<Expression> values, int line) {
        super(AccessIdentifier.NONE, line);
        this.values = values;
    }
}
