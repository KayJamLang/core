package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Type;

import java.util.Collections;

public class NamedExpression extends FunctionRefExpression {
    /**
     * Name for call named function
     */
    public final String name;

    /**
     * @param name Name for call named function
     * @param expression Body
     * @param line Start line
     */
    public NamedExpression(String name, Expression expression, int line) {
        super(Collections.emptyList(), expression, Type.ANY, line);
        this.name = name;
    }
}
