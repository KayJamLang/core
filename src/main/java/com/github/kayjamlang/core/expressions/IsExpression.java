package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class IsExpression extends Expression {
    public final Expression expression;
    public final Type verifyType;

    public IsExpression(Expression expression, Type verifyType, int line) {
        super(AccessIdentifier.NONE, line);
        this.expression = expression;
        this.verifyType = verifyType;
    }
}
