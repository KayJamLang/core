package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.Annotation;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.List;

public class Function extends Container {
    public final String name;
    public final Type returnType;
    public final List<Argument> arguments;
    public final List<Annotation> annotations;

    public Function(String name, List<Expression> children,
                    AccessIdentifier identifier, List<Argument> arguments,
                    int line, Type returnType, List<Annotation> annotations) {
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

    @Deprecated
    public Object call(Container parent, Container argsParent, List<Object> arguments) throws Exception {
        /*classes.putAll(argsParent.classes);
        mainContainer = argsParent.mainContainer;

        if (parent instanceof ObjectContainer) {
            variables.putAll(parent.variables);
            functions.addAll(parent.functions);
        }

        for (int i = 0; i < arguments.size(); i++)
            variables.put(this.arguments.get(i), arguments.get(i));*/

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

    public static class Argument {
        public final Type type;
        public final String name;

        public Argument(Type type, String name) {
            this.type = type;
            this.name = name;
        }
    }
}

