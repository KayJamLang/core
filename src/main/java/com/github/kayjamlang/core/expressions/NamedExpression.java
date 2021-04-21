package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Type;

import java.util.Collections;

public class NamedExpression extends FunctionRefExpression {
    public final String name;

    public NamedExpression(String name, Expression expression, int line) {
        super(Collections.emptyList(), expression, Type.ANY, line);
        this.name = name;
    }
}
