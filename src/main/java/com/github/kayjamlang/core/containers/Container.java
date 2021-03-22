package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.List;

public class Container extends Expression implements Cloneable {
    public ArrayList<Expression> children = new ArrayList<>();
    public ArrayList<Function> functions = new ArrayList<>();

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
    @SuppressWarnings("unchecked")
    public Container clone() throws CloneNotSupportedException {
        Container container = (Container) super.clone();
        container.children = (ArrayList<Expression>) children.clone();
        container.functions = (ArrayList<Function>) functions.clone();
        return container;
    }
}

