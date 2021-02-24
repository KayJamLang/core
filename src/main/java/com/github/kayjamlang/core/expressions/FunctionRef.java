package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.List;

public class FunctionRef extends Expression {

    public final Expression expression;
    public final List<String> arguments;

    private Container parent;

    public FunctionRef(List<String> arguments, Expression expression, int line) {
        super(AccessIdentifier.NONE, line);
        this.expression = expression;
        this.arguments = arguments;
    }
}
