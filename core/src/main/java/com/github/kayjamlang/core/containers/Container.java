package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.ArrayList;
import java.util.List;

/**
 * Expression group
 */
public class Container extends Expression implements Cloneable {
    /**
     * Expressions in container
     */
    public List<Expression> children = new ArrayList<>();

    /**
     * Functions in group
     */
    public List<FunctionContainer> functions = new ArrayList<>();

    /**
     * Type of container is used only for other containers
     * @see #Container(List, int)
     *
     * @param children Expressions in group
     * @param identifier Type of container
     * @param line Line of container
     */
    public Container(List<Expression> children, AccessType identifier, int line) {
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
        this(children, AccessType.NONE, line);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Container clone() throws CloneNotSupportedException {
        Container container = (Container) super.clone();
        container.children = (List<Expression>) ((ArrayList<Expression>) children).clone();
        container.functions = (List<FunctionContainer>) ((ArrayList<FunctionContainer>) functions).clone();
        return container;
    }

    @Override
    public boolean isConstant() {
        return false;
    }
}

