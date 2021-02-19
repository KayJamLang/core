package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.List;

public class FunctionRef extends Expression {

    public final Expression expression;
    public final List<String> arguments;

    private Container parent;

    public FunctionRef(List<String> arguments, Expression expression, int line) {
        super(AccessIdentifier.NONE, line);
        this.expression = expression;
        this.arguments = arguments;
    }

    public boolean accept(List<Object> arguments){
        return this.arguments.size()==arguments.size();
    }

    public Object call(List<Object> arguments) throws Exception {
        ArrayList<Expression> expressions = new ArrayList<>();
        expressions.add(expression);

        Container container = new Container(expressions, AccessIdentifier.NONE, line);
        container.mainContainer = parent.mainContainer;

        for (int i = 0; i < arguments.size(); i++)
            container.variables.put(this.arguments.get(i), arguments.get(i));

        return container.execute(container, container);
    }

    @Override
    public Object execute(Container parent, Container argsParent) {
        this.parent = parent;
        return this;
    }
}
