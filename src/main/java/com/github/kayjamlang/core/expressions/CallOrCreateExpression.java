package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.List;

public class CallOrCreateExpression extends Expression {

    public final String functionName;
    public final List<Expression> arguments;

    public CallOrCreateExpression(String functionName, List<Expression> arguments, int line){
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
