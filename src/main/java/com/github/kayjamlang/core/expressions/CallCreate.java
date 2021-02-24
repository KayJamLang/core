package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.List;

public class CallCreate extends Expression {

    public final String functionName;
    public final List<Expression> arguments;

    public CallCreate(String functionName, List<Expression> arguments, int line){
        super(AccessIdentifier.NONE, line);
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "FunctionCall{" +
                "functionName='" + functionName + '\'' +
                ", arguments=" + arguments +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}
