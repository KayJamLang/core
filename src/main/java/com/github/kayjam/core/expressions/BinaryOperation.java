package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.Token;
import com.github.kayjam.core.opcodes.AccessIdentifier;

public class BinaryOperation extends Expression {
    public final Token operation;
    public final Expression left;
    public final Expression right;

    public BinaryOperation(Expression left, Expression right, Token operation, int line) {
        super(AccessIdentifier.NONE, line);
        this.left = left;
        this.right = right;
        this.operation = operation;
    }
}
