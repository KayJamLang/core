package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class GetExpression extends Expression {

    public final Expression root;
    public final Expression value;

    public GetExpression(Expression root, Expression value, int line) {
        super(AccessType.NONE, line);
        this.root = root;
        this.value = value;
    }
}
