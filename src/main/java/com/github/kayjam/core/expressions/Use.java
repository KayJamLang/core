package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.Type;
import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.opcodes.AccessIdentifier;
import com.github.kayjam.core.provider.Context;

public class Use extends Expression {
    public final Expression expression;

    public Use(Expression expression, int line) {
        super(AccessIdentifier.NONE, line);
        this.expression = expression;
    }

    @Override
    public Object execute(Container parent, Container varParent) throws Exception {
        if(!(expression instanceof Const)||!(expression.execute(parent, varParent) instanceof String))
            throw new RuntimeException("expected string");

        UseInterface useInterface = (UseInterface) parent.mainContainer.data.get("useInterface");
        Expression expr =
                useInterface.getExpression(expression.execute(parent, varParent).toString());

        Object value = expr.execute(parent, varParent);
        if(expr instanceof Container){
            Container container = (Container) expr;
            parent.functions.addAll(container.functions);
            parent.variables.putAll(container.variables);
            parent.classes.putAll(container.classes);
        }

        return value;
    }

    public interface UseInterface{
        Expression getExpression(String path) throws Exception;
    }
}