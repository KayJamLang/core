package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.List;

public class CallOrCreateExpression extends Expression {

    @Deprecated
    public final String functionName = null;

    public final String name;
    public final List<Expression> arguments;

    public CallOrCreateExpression(String name, List<Expression> arguments, int line){
        super(AccessType.NONE, line);
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "FunctionCall{" +
                "functionName='" + name + '\'' +
                ", arguments=" + arguments +
                ", identifier=" + accessType +
                ", line=" + line +
                '}';
    }
}
