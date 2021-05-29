package com.github.kayjamlang.core.provider;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.TypeException;

public abstract class ExpressionProvider<A, B, C, D> {
    /**
     * @deprecated please replace to {@code mainProvider.defaultObject}
     */
    @Deprecated
    public B provide(MainExpressionProvider<B, C, D> mainProvider, C context, C argsContext, A expression) {
        return mainProvider.defaultObject;
    }

    /**
     * @deprecated please don't use this
     */
    @Deprecated
    public Type getType(MainExpressionProvider<B, C, D> mainProvider, C context, C argsContext, A expression) throws TypeException {
        return Type.ANY;
    }
}
