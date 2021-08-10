package com.github.kayjamlang.core.provider;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.ValueExpression;

public class ValueExpressionProvider<A, B, C> extends ExpressionProvider<ValueExpression,
        A, B, C> {

    @Override
    public final Type getType(MainExpressionProvider<A, B, C> mainProvider,
                        B context,
                        B argsContext,
                        ValueExpression expression) throws TypeException {
        if(expression.value == null) {
            Type any = Type.NULL.clone();
            any.nullable = true;

            return any;
        }

        if(expression.value instanceof Double)
            return Type.DOUBLE;
        else if(expression.value instanceof Integer)
            return Type.INTEGER;
        else if(expression.value instanceof Long)
            return Type.LONG;
        else if(expression.value instanceof String)
            return Type.STRING;
        else if(expression.value instanceof Boolean)
            return Type.BOOLEAN;

        return Type.ANY;
    }
}
