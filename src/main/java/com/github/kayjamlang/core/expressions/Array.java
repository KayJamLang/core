package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.List;

public class Array extends Expression {

    public final List<Expression> values;

    public Array(List<Expression> values, int line) {
        super(AccessIdentifier.NONE, line);
        this.values = values;
    }
}
