package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.opcodes.AccessIdentifier;

import java.util.List;

public class Array extends Expression {

    public final List<Expression> values;

    public Array(List<Expression> values, int line) {
        super(AccessIdentifier.NONE, line);
        this.values = values;
    }

    @Override
    public Object execute(Container parent, Container varParent) throws Exception {
        return super.execute(parent, varParent);
    }
}
