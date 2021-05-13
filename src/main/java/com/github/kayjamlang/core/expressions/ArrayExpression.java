package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.List;

public class ArrayExpression extends Expression {

    public final List<Expression> values;

    public ArrayExpression(List<Expression> values, int line) {
        super(AccessType.NONE, line);
        this.values = values;
    }
}
