package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.expressions.FunctionRef;

import java.util.Collections;

public class NamedExpression extends FunctionRef {
    public final String name;

    public NamedExpression(String name, Expression expression, int line) {
        super(Collections.emptyList(), expression, line);
        this.name = name;
    }
}
