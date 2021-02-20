package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.Type;
import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.opcodes.AccessIdentifier;
import com.github.kayjam.core.provider.Context;
import org.omg.CORBA.Any;

public class If extends Expression{
    public final Expression condition;
    public final Expression ifTrue;
    public final Expression ifFalse;

    public If(Expression condition, Expression ifTrue, Expression ifFalse, int line) {
        super(AccessIdentifier.NONE, line);

        this.condition = condition;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    @Override
    public String toString() {
        return "If{" +
                "condition=" + condition +
                ", ifTrue=" + ifTrue +
                ", ifFalse=" + ifFalse +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}