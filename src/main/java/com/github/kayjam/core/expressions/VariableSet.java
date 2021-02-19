package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.exceptions.runtime.NotFoundException;
import com.github.kayjam.core.opcodes.AccessIdentifier;

public class VariableSet extends Variable {

    public VariableSet(String name, Expression expression, int line) {
        super(name, expression, AccessIdentifier.NONE, line);
    }

    @Override
    public Object execute(Container parent, Container argsParent) throws Exception {
        if(!parent.variables.containsKey(name))
            throw new NotFoundException(this, "variable", name);

        Object value = expression.execute(parent, argsParent);
        parent.variables.put(name, value);
        return value;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}