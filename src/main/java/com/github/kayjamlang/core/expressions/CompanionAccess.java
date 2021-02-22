package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.exceptions.runtime.NotFoundException;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

public class CompanionAccess extends Expression {

    public final String className;
    public final Expression child;

    public CompanionAccess(String className, Expression child, int line) {
        super(AccessIdentifier.NONE, line);
        this.className = className;
        this.child = child;
    }

    @Override
    public Object execute(Container parent, Container argsParent) throws Exception {
        if(!parent.classes.containsKey(className))
            throw new NotFoundException(this, "class", className);

        ObjectContainer companion = parent.classes.get(className).companion;
        if(companion==null)
            throw new NotFoundException(this, "class companion in class \""+className+"\"");

        companion.mainContainer = argsParent.mainContainer;
        companion.classes.putAll(argsParent.classes);
        companion.execute(companion, argsParent);

        return child.execute(companion, argsParent);
    }
}