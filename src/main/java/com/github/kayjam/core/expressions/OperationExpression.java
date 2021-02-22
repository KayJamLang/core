package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.Operation;
import com.github.kayjam.core.Token;
import com.github.kayjam.core.opcodes.AccessIdentifier;

public class OperationExpression extends Expression {

    public final Operation operation;
    public final Expression left;
    public final Expression right;

    public OperationExpression(Expression left, Expression right, Token operation, int line) {
        super(AccessIdentifier.NONE, line);
        this.left = left;
        this.right = right;
        this.operation = Operation.get(operation.type);
    }

    public OperationExpression(Expression left, Expression right, Operation operation, int line) {
        super(AccessIdentifier.NONE, line);
        this.left = left;
        this.right = right;
        this.operation = operation;
    }
}
