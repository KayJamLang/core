package com.github.kayjamlang.core.provider;

import com.github.kayjamlang.core.Expression;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MainExpressionProvider<ReturnObject> {
    private final Map<Class<? extends Expression>,
            ExpressionProvider<? extends Expression, ReturnObject>> compilers = new HashMap<>();
    private final ReturnObject defaultObject;

    public MainContext mainContext = new MainContext(null, null);

    public MainExpressionProvider(ReturnObject defaultObject){
        this.defaultObject = defaultObject;
    }

    public <ExpressionType extends Expression> void addCompiler(
            Class<ExpressionType> expression,
            ExpressionProvider<ExpressionType, ReturnObject> expressionCompiler) {
        compilers.put(expression, expressionCompiler);
    }

    @SuppressWarnings("unchecked")
    public ReturnObject provide(Expression expression, Context context, Context argsContext) throws Exception {
        if(compilers.containsKey(expression.getClass())){
            ExpressionProvider<? extends Expression, ReturnObject> expressionCompiler =
                    compilers.get(expression.getClass());
            Method method = expressionCompiler.getClass()
                    .getDeclaredMethod("provide", MainExpressionProvider.class,
                            Context.class,
                            Context.class,
                            Object.class);

            return (ReturnObject)
                    method.invoke(expressionCompiler,
                            this, context, argsContext, expression);
        }

        return defaultObject;
    }
}
