package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.opcodes.AccessIdentifier;

public class Access extends Expression {
    public final Expression root;
    public final Expression child;

    public Access(Expression root, Expression child, int line) {
        super(AccessIdentifier.NONE, line);
        this.root = root;
        this.child = child;
    }

    @Override
    public Object execute(Container parent, Container argsParent) throws Exception {
        Object rootObject = root.execute(parent, argsParent);
        if(!(rootObject instanceof Container))
            throw new Exception();

        return child.execute((Container) rootObject, argsParent);
    }

    @Override
    public String toString() {
        return "Access{" +
                "root=" + root +
                ", child=" + child +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}