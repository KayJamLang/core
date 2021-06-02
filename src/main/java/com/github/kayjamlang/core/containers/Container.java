package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Stmt;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.ArrayList;
import java.util.List;

/**
 * Expression group
 */
public class Container extends Stmt implements Cloneable {
    /**
     * Expressions in container
     */
    public List<Stmt> children = new ArrayList<>();

    /**
     * Functions in group
     */
    public List<FunctionContainer> functions = new ArrayList<>();

    /**
     * Create group of container
     * @param children Expressions in group
     * @param line Line of container
     */
    public Container(List<Stmt> children, int line) {
        super(line);

        for(Stmt expression: children){
            if(expression instanceof FunctionContainer)
                functions.add((FunctionContainer) expression);
            else this.children.add(expression);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Container clone() throws CloneNotSupportedException {
        Container container = (Container) super.clone();
        container.children = (List<Stmt>) ((ArrayList<Stmt>) children).clone();
        container.functions = (List<FunctionContainer>) ((ArrayList<FunctionContainer>) functions).clone();
        return container;
    }
}

