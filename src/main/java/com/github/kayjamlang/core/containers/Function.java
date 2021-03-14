package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Argument;
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

