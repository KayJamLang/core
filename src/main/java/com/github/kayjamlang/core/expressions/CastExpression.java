package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.opcodes.AccessType;

public class CastExpression extends Expression {

    public final Expression expression;
    public final Type castType;

    public CastExpression(Expression expression, Type castType, int line) {
        super(AccessType.NONE, line);
        this.expression = expression;
        this.castType = castType;
    }
}
