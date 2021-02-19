package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.exceptions.runtime.NotFoundException;
import com.github.kayjam.core.opcodes.AccessIdentifier;

import java.util.ArrayList;

public class VariableLink extends Expression {
    public final String name;

    public VariableLink(String name, int line) {
        super(AccessIdentifier.NONE, line);
        this.name = name;
    }

    @Override
    public Object execute(Container parent, Container argsParent) throws Exception {
        if(parent.variables.containsKey(name))
            return parent.variables.get(name);

        throw new NotFoundException(this, "variable", name);
    }

    @Override
    public String toString() {
        return "VariableLink{" +
                "name='" + name + '\'' +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}
