package com.github.kayjam.core.containers;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.expressions.Annotation;
import com.github.kayjam.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.List;

public class Function extends Container {
    public final String name;
    public final String returnType;
    public final List<String> arguments;
    public final List<Annotation> annotations;

    public Function(String name, List<Expression> children,
                    AccessIdentifier identifier, List<String> arguments,
                    int line, String returnType, List<Annotation> annotations) {
        super(children, identifier, line);

        this.name = name;
        this.returnType = returnType;
        this.arguments = arguments;
        this.annotations = annotations;
        globalVariables = false;
        globalFunctions = false;
    }

    public boolean accept(String name, List<Object> arguments){
        return this.name.equals(name)
                &&this.arguments.size()==arguments.size();
    }

    public Object call(Container parent) throws Exception {
        mainContainer = parent.mainContainer;
        globalVariables = false;

        return super.onExecute(parent);
    }

    @Override
    public Object onExecute(Container parent) throws Exception {
        return null;
    }

    public Object call(Container parent, Container argsParent, List<Object> arguments) throws Exception {
        classes.putAll(argsParent.classes);
        mainContainer = argsParent.mainContainer;

        if (parent instanceof ObjectContainer) {
            variables.putAll(parent.variables);
            functions.addAll(parent.functions);
        }

        for (int i = 0; i < arguments.size(); i++)
            variables.put(this.arguments.get(i), arguments.get(i));

        return call(this);
    }

    @Override
    public String toString() {
        return "Function{" +
                "name='" + name + '\'' +
                ", returnType='" + returnType + '\'' +
                ", arguments=" + arguments +
                ", annotations=" + annotations +
                ", children=" + children +
                ", functions=" + functions +
                ", classes=" + classes +
                ", variables=" + variables +
                ", globalVariables=" + globalVariables +
                ", globalFunctions=" + globalFunctions +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}

