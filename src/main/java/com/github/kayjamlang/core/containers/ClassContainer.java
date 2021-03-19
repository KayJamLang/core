package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.List;

public class ClassContainer extends ObjectContainer {

    public final String name;
    public final String extendsClass;
    public final List<String> implementsClass;
    public ArrayList<ConstructorContainer> constructors = new ArrayList<>();
    public ObjectContainer companion;

    public ClassContainer(String name, String extendsClass, List<String> implementsClass,
                          List<Expression> children, AccessIdentifier identifier, int line) throws Exception {
        super(children, identifier, line);
        this.name = name;
        this.extendsClass = extendsClass;
        this.implementsClass = implementsClass;

        for(Expression expression: children) {
            if (expression instanceof ObjectContainer
                    && expression.identifier == AccessIdentifier.COMPANION) {
                if (companion != null)
                    throw new ParserException(expression.line, "companion already exists");

                companion = (ObjectContainer) expression;
            }else if(expression instanceof ConstructorContainer){
                constructors.add((ConstructorContainer) expression);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ClassContainer clone() throws CloneNotSupportedException {
        ClassContainer classContainer = (ClassContainer) super.clone();
        classContainer.constructors = (ArrayList<ConstructorContainer>)
                constructors.clone();
        return classContainer;
    }
}
