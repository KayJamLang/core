package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

public class GetExpression extends Expression {
    /**
     * Expression for get
     */
    public final Expression root;
    /**
     * Index for get
     */
    public final Expression value;

    /**
     * @param root Expression for get
     * @param value Index for get
     * @param line Start line
     */
    public GetExpression(Expression root, Expression value, int line) {
        super(AccessType.NONE, line);
        this.root = root;
        this.value = value;
    }
}
