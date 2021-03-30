package com.github.kayjamlang.core.provider;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.Type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MainExpressionProvider<ReturnObject, ContextObject, MainContextObject> {
    private final Map<Class<? extends Expression>,
            ExpressionProvider<? extends Expression, ReturnObject, ContextObject, MainContextObject>> providers = new HashMap<>();
    public final ReturnObject defaultObject;
    public MainContextObject mainContext;

    public MainExpressionProvider(ReturnObject defaultObject){
        this.defaultObject = defaultObject;
    }

    public <ExpressionType extends Expression> void addProvider(
            Class<ExpressionType> expression,
            ExpressionProvider<ExpressionType, ReturnObject, ContextObject, MainContextObject> expressionCompiler) {
        providers.put(expression, expressionCompiler);
    }



    @SuppressWarnings("unchecked")
    public ReturnObject provide(Expression expression, ContextObject context,
                                ContextObject argsContext) throws Exception {
        if(providers.containsKey(expression.getClass())){
            ExpressionProvider<? extends Expression, ReturnObject, ContextObject, MainContextObject> expressionCompiler =
                    providers.get(expression.getClass());
            Method method = expressionCompiler.getClass()
                    .getMethod("provide",
                            MainExpressionProvider.class,
                            Object.class,
                            Object.class,
                            Object.class);

            try {
                return (ReturnObject)
                        method.invoke(expressionCompiler,
                                this, context, argsContext, expression);
            } catch (InvocationTargetException ite) {
                if(ite.getCause() instanceof Exception)
                    throw (Exception) ite.getCause();
                else ite.getCause().printStackTrace();
            }
        }

        return defaultObject;
    }

    public Type getType(Expression expression, ContextObject context,
                                ContextObject argsContext) throws Exception {
        if(providers.containsKey(expression.getClass())){
            ExpressionProvider<? extends Expression, ReturnObject, ContextObject, MainContextObject> expressionCompiler =
                    providers.get(expression.getClass());
            Method method = expressionCompiler.getClass()
                    .getMethod("getType",
                            MainExpressionProvider.class,
                            Object.class, Object.class, Object.class);

            try {
                return (Type)
                        method.invoke(expressionCompiler,
                                this, context, argsContext, expression);
            } catch (InvocationTargetException ite) {
                if(ite.getCause() instanceof Exception)
                    throw (Exception) ite.getCause();
                else ite.getCause().printStackTrace();
            }
        }

        return Type.ANY;
    }
}
