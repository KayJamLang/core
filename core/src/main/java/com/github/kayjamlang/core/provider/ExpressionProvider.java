package com.github.kayjamlang.core.provider;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.TypeException;

public abstract class ExpressionProvider<A, B, C, D> {

    public B provide(MainExpressionProvider<B, C, D> mainProvider, C context, C argsContext, A expression) {
        return mainProvider.defaultObject;
    }

    public Type getType(MainExpressionProvider<B, C, D> mainProvider, C context, C argsContext, A expression) throws Exception {
        return Type.ANY;
    }
}
