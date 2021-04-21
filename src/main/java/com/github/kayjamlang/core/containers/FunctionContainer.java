package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Annotation;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.List;

public class FunctionContainer extends Container {
    public final String name;
    public final Type returnType;
    public final List<Argument> arguments;
    public final List<Annotation> annotations;

    public FunctionContainer(String name, List<Expression> children,
                             AccessIdentifier identifier, List<Argument> arguments,
                             int line, Type returnType, List<Annotation> annotations) {
        super(children, identifier, line);

        this.name = name;
        this.returnType = returnType;
        this.arguments = arguments;
        this.annotations = annotations;
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
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }


}

