package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Stmt;
import com.github.kayjamlang.core.Type;

import java.util.Collections;

public class NamedExpression extends FunctionRefExpression {
    /**
     * Name for call named function
     */
    public final String name;

    /**
     * @param name Name for call named function
     * @param stmt Body
     * @param line Start line
     */
    public NamedExpression(String name, Stmt stmt, int line) {
        super(Collections.emptyList(), stmt, Type.ANY, line);
        this.name = name;
    }
}
