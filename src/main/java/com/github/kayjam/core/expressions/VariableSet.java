package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.Type;
import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.exceptions.runtime.NotFoundException;
import com.github.kayjam.core.opcodes.AccessIdentifier;
import com.github.kayjam.core.provider.Context;

public class VariableSet extends Variable {

    public VariableSet(String name, Expression expression, int line) {
        super(name, expression, AccessIdentifier.NONE, line);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}