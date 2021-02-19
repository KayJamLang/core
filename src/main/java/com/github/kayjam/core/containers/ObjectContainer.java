package com.github.kayjam.core.containers;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.expressions.Variable;
import com.github.kayjam.core.opcodes.AccessIdentifier;

import java.util.List;

public class ObjectContainer extends Container {

    public ObjectContainer(List<Expression> children, AccessIdentifier identifier, int line) {
        super(children, identifier, line);
        globalVariables = false;
        globalFunctions = false;
    }

    @Override
    public Object onExecute(Container parent) throws Exception {
        variables.clear();
        variables.put("this", this);

        for (Expression expression: children)
            if(expression instanceof Variable)
                expression.execute(this);

        return this;
    }
}
