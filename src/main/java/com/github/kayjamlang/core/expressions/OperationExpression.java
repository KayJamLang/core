package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.expressions.data.Operation;
import com.github.kayjamlang.core.Token;
import com.github.kayjamlang.core.opcodes.AccessType;

public class OperationExpression extends Expression {
    /**
     * Operation type
     */
    public final Operation operation;

    /**
     * Primary value
     */
    public final Expression left;

    /**
     * Second value
     */
    public final Expression right;

    /**
     * @param left Primary value
     * @param right Second value
     * @param operation Operation type
     * @param line Start line
     */
    public OperationExpression(Expression left, Expression right, Operation operation, int line) {
        super(AccessType.NONE, line);
        this.left = left;
        this.right = right;
        this.operation = operation;
    }
}
