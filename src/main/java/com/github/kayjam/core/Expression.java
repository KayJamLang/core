package com.github.kayjam.core;

import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.opcodes.AccessIdentifier;

public abstract class Expression {
    public final AccessIdentifier identifier;
    public final int line;

    public Expression(AccessIdentifier identifier, int line) {
        this.identifier = identifier;
        this.line = line;
    }

    public Object execute(Container parent) throws Exception {
        return execute(parent, parent);
    }

    public Object execute(Container parent, Container varParent) throws Exception {
        return null;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}