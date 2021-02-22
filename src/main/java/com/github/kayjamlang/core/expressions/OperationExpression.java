package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.Operation;
import com.github.kayjamlang.core.Token;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

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
