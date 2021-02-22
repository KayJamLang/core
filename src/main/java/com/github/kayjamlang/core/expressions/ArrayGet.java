package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class ArrayGet extends Expression {

    public final Expression root;
    public final Expression value;

    public ArrayGet(Expression root, Expression value, int line) {
        super(AccessIdentifier.NONE, line);
        this.root = root;
        this.value = value;
    }
}