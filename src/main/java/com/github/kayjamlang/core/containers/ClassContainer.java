package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.exceptions.CompileException;
import com.github.kayjamlang.core.expressions.Variable;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.List;

public class ClassContainer extends ObjectContainer {

    public final String name;
    public final String extendsClass;
    public final List<String> implementsClass;
    public final List<ConstructorContainer> constructors = new ArrayList<>();
    public ObjectContainer companion;

    public ClassContainer(String name, String extendsClass, List<String> implementsClass,
                          List<Expression> children, AccessIdentifier identifier, int line) throws Exception {
        super(children, identifier, line);
        this.name = name;
        this.extendsClass = extendsClass;
        this.implementsClass = implementsClass;
        globalVariables = false;
        globalFunctions = false;

        for(Expression expression: children) {
            if (expression instanceof ObjectContainer
                    && expression.identifier == AccessIdentifier.COMPANION) {
                if (companion != null)
                    throw new CompileException(expression.line, "companion already exists");

                companion = (ObjectContainer) expression;
            }else if(expression instanceof ConstructorContainer){
                constructors.add((ConstructorContainer) expression);
            }
        }
    }

    @Override
    public Object onExecute(Container parent) throws Exception {
        mainContainer = parent.mainContainer;
        return null;
    }

    public static class Class extends ClassContainer{

        public Class(ClassContainer classContainer, int lineCreate) throws Exception {
            super(classContainer.name, classContainer.extendsClass,
                    classContainer.implementsClass,
                    classContainer.children,
                    classContainer.identifier, lineCreate);
            mainContainer = classContainer.mainContainer;
            functions.addAll(classContainer.functions);
            variables.putAll(classContainer.variables);
            classes.putAll(classContainer.classes);
            companion = classContainer.companion;
        }

        @Override
        public Object onExecute(Container parent) throws Exception {
            parent.variables.put("this", this);
            mainContainer = parent.mainContainer;

            for (Expression expression: children)
                if(expression instanceof Variable)
                    expression.execute(this);

            return this;
        }
    }
}
