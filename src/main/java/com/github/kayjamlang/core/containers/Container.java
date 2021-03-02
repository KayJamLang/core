package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.List;

public class Container extends Expression implements Cloneable {
    public final List<Expression> children = new ArrayList<>();
    public final List<Function> functions = new ArrayList<>();

    public Container(List<Expression> children, AccessIdentifier identifier, int line) {
        super(identifier, line);

        for(Expression expression: children){
            if(expression instanceof Function)
                functions.add((Function) expression);
            else this.children.add(expression);
        }
    }

    @Override
    public String toString() {
        return "Container{" +
                "children=" + children +
                ", functions=" + functions +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
