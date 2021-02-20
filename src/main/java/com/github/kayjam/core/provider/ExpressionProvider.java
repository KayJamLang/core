package com.github.kayjam.core.provider;

public class ExpressionProvider<ExpressionType, ReturnObject> {

    public ReturnObject provide(MainExpressionProvider<ReturnObject> mainProvider,
                                Context context,
                                Context argsContext,
                                ExpressionType expression) throws Exception {
        return null;
    }
}
