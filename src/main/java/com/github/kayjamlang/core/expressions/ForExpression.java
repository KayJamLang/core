package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class ForExpression extends Expression {

    public final String variableName;
    public final Expression range;
    public final Expression body;

    public ForExpression(String variableName, Expression range, Expression body, int line) {
        super(AccessIdentifier.NONE, line);
        this.variableName = variableName;
        this.range = range;
        this.body = body;
    }
}
