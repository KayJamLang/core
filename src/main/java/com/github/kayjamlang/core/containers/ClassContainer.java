package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.expressions.VariableExpression;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.ArrayList;
import java.util.List;

/**
 * Class expression group
 */
public class ClassContainer extends Container {
    /**
     * Name of class
     */
    public final String name;

    /**
     * Name of class parent
     */
    public final String extendsClass;

    /**
     * Classes implements
     */
    public final List<String> implementsClass;

    /**
     * Static companion of class
     */
    public ObjectContainer companion;

    /**
     * Class constructors
     */
    public List<ConstructorContainer> constructors = new ArrayList<>();

    /**
     * Properties in class
     */
    public List<VariableExpression> variables = new ArrayList<>();

    /**
     * @param name Name of class
     * @param extendsClass Name of class parent
     * @param implementsClass Classes implements
     * @param children Code in class (Only functions, constructors, properties and companion)
     * @param accessType Access of class
     * @param line Line of start class
     * @throws ParserException throws on unexpected expression in code
     */
    public ClassContainer(String name, String extendsClass, List<String> implementsClass,
                          List<Expression> children, AccessType accessType, int line) throws ParserException {
        super(new ArrayList<>(), accessType, line);
        this.name = name;
        this.extendsClass = extendsClass;
        this.implementsClass = implementsClass;

        for(Expression expression: children) {
            if (expression instanceof ObjectContainer
                    && expression.accessType == AccessType.COMPANION) {
                if (companion != null)
                    throw new ParserException(expression.line, "companion already exists");

                companion = (ObjectContainer) expression;
                this.children.remove(expression);
            }else if(expression instanceof ConstructorContainer){
                constructors.add((ConstructorContainer) expression);
                this.children.remove(expression);
            }else if(expression instanceof VariableExpression)
                variables.add((VariableExpression) expression);
            else if(expression instanceof FunctionContainer){
                functions.add((FunctionContainer) expression);
            }else throw new ParserException(line,
                        "The class can only contain variables and functions");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ClassContainer clone() throws CloneNotSupportedException {
        ClassContainer classContainer = (ClassContainer) super.clone();
        classContainer.constructors = (List<ConstructorContainer>) ((ArrayList<ConstructorContainer>) constructors).clone();
        classContainer.variables = (List<VariableExpression>) ((ArrayList<VariableExpression>) variables).clone();
        return classContainer;
    }

    @Override
    public String toString() {
        return name+"@class";
    }
}
