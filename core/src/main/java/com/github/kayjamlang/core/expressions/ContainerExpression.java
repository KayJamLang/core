package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.statements.FunctionStatement;
import com.github.kayjamlang.core.statements.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Expression group
 */
public class ContainerExpression extends Expression implements Cloneable {
    /**
     * Statements in container
     */
    public List<Statement> children = new ArrayList<>();

    /**
     * Functions in group
     */
    public List<FunctionStatement> functions = new ArrayList<>();

    /**
     * Create group of container
     *
     * @param children Statements in group
     * @param line Line of container
     */
    public ContainerExpression(List<Statement> children, int line) {
        super(line);

        for(Statement statement: children){
            if(statement instanceof FunctionStatement)
                functions.add((FunctionStatement) statement);
            else this.children.add(statement);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ContainerExpression clone() throws CloneNotSupportedException {
        ContainerExpression containerExpression = (ContainerExpression) super.clone();
        containerExpression.children = (List<Statement>) ((ArrayList<Statement>) children).clone();
        containerExpression.functions = (List<FunctionStatement>) ((ArrayList<FunctionStatement>) functions).clone();
        return containerExpression;
    }

    @Override
    public boolean isConstant() {
        return false;
    }
}

