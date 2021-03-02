package com.github.kayjamlang.core.provider;

public class ExpressionProvider<ExpressionType, ReturnObject, ContextObject, MainContextObject> {

    public ReturnObject provide(MainExpressionProvider<ReturnObject, ContextObject,
                                        MainContextObject> mainProvider,
                                ContextObject context,
                                ContextObject argsContext,
                                ExpressionType expression) throws Exception {
        return null;
    }
}
