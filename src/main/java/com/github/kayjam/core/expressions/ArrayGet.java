package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.opcodes.AccessIdentifier;

public class ArrayGet extends Expression {

    public final Expression root;
    public final Expression value;

    public ArrayGet(Expression root, Expression value, int line) {
        super(AccessIdentifier.NONE, line);
        this.root = root;
        this.value = value;
    }
}
