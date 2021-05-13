package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Expression group
 */
public class Container extends Expression implements Cloneable {
    /**
     * Expressions in container
     */
    public ArrayList<Expression> children = new ArrayList<>();

    /**
     * Functions in group
     */
    public ArrayList<FunctionContainer> functions = new ArrayList<>();

    /**
     * Type of container is used only for other containers
     * @see #Container(List, int)
     *
     * @param children Expressions in group
     * @param identifier Type of container
     * @param line Line of container
     */
    public Container(List<Expression> children, AccessIdentifier identifier, int line) {
        super(identifier, line);

        for(Expression expression: children){
            if(expression instanceof FunctionContainer)
                functions.add((FunctionContainer) expression);
            else this.children.add(expression);
        }
    }

    /**
     * Create group of container
     * @param children Expressions in group
     * @param line Line of container
     */
    public Container(List<Expression> children, int line) {
        this(children, AccessIdentifier.NONE, line);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Container clone() throws CloneNotSupportedException {
        Container container = (Container) super.clone();
        container.children = (ArrayList<Expression>) children.clone();
        container.functions = (ArrayList<FunctionContainer>) functions.clone();
        return container;
    }
}

