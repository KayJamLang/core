package com.github.kayjamlang.core.provider;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.TypeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MainExpressionProvider<A, B, C> {
    private final Map<Class<? extends Expression>,
            ExpressionProvider<? extends Expression, A, B, C>> providers = new HashMap<>();

    public final A defaultObject;

    public MainExpressionProvider(A defaultObject){
        this.defaultObject = defaultObject;
    }

    public <D extends Expression> void addProvider(
            Class<D> expression,
            ExpressionProvider<D, A, B, C> expressionCompiler) {
        providers.put(expression, expressionCompiler);
    }

    public <D extends Expression> void removeProvider(
            Class<D> expression){
        providers.remove(expression);
    }


    @SuppressWarnings("unchecked")
    public A provide(Expression expression, B context,
                     B argsContext) throws Exception {
        if(providers.containsKey(expression.getClass())){
            ExpressionProvider<? extends Expression, A, B, C> expressionCompiler =
                    providers.get(expression.getClass());
            Method method = expressionCompiler.getClass()
                    .getMethod("provide",
                            MainExpressionProvider.class,
                            Object.class,
                            Object.class,
                            Object.class);

            try {
                return (A)
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

    public Type getType(Expression expression, B context,
                                B argsContext) throws TypeException {
        if(providers.containsKey(expression.getClass())){
            ExpressionProvider<? extends Expression, A, B, C> expressionCompiler =
                    providers.get(expression.getClass());
            try {
                Method method = expressionCompiler.getClass()
                        .getMethod("getType",
                                MainExpressionProvider.class,
                                Object.class, Object.class, Object.class);

                return (Type)
                        method.invoke(expressionCompiler,
                                this, context, argsContext, expression);
            } catch (Throwable ignored){}
        }

        return Type.ANY;
    }
}
