package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.expressions.Variable;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.List;

public class ClassContainer extends Container {

    public final String name;
    public final String extendsClass;
    public final List<String> implementsClass;
    public ObjectContainer companion;

    public ArrayList<ConstructorContainer> constructors = new ArrayList<>();
    public ArrayList<Variable> variables = new ArrayList<>();

    public ClassContainer(String name, String extendsClass, List<String> implementsClass,
                          List<Expression> children, AccessIdentifier identifier, int line) throws Exception {
        super(new ArrayList<>(), identifier, line);
        this.name = name;
        this.extendsClass = extendsClass;
        this.implementsClass = implementsClass;

        for(Expression expression: children) {
            if (expression instanceof ObjectContainer
                    && expression.identifier == AccessIdentifier.COMPANION) {
                if (companion != null)
                    throw new ParserException(expression.line, "companion already exists");

                companion = (ObjectContainer) expression;
                this.children.remove(expression);
            }else if(expression instanceof ConstructorContainer){
                constructors.add((ConstructorContainer) expression);
                this.children.remove(expression);
            }else if(expression instanceof Variable)
                variables.add((Variable) expression);
            else if(expression instanceof Function){
                functions.add((Function) expression);
            }else throw new ParserException(line,
                        "The class can only contain variables and functions");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ClassContainer clone() throws CloneNotSupportedException {
        ClassContainer classContainer = (ClassContainer) super.clone();
        classContainer.constructors = (ArrayList<ConstructorContainer>)
                constructors.clone();
        classContainer.variables = (ArrayList<Variable>) variables.clone();
        return classContainer;
    }

    @Override
    public String toString() {
        return name+"@class";
    }
}
