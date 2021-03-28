package com.github.kayjamlang.core.provider;

import com.github.kayjamlang.core.Type;

public abstract class ExpressionProvider<ExpressionType, ReturnObject, ContextObject, MainContextObject> {

    public ReturnObject provide(MainExpressionProvider<ReturnObject, ContextObject,
                                        MainContextObject> mainProvider,
                                ContextObject context,
                                ContextObject argsContext,
                                ExpressionType expression) {
        return mainProvider.defaultObject;
    }

    public Type getType(MainExpressionProvider<ReturnObject, ContextObject,
            MainContextObject> mainProvider,
                        ContextObject context,
                        ContextObject argsContext,
                        ExpressionType expression) throws Exception {
        return Type.ANY;
    }
}
