package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.Arrays;
import java.util.List;

public class CallOrCreateExpression extends Expression {
    /**
     * @deprecated replaced to {@link #name}
     */
    @Deprecated
    public final String functionName = null;

    /**
     * Call object name
     */
    public final String name;

    /**
     * Arguments for call or create
     */
    public final List<Expression> arguments;

    /**
     * @param name Call object name
     * @param arguments Arguments for call or create
     * @param line Start line
     */
    public CallOrCreateExpression(String name, List<Expression> arguments, int line){
        super(AccessType.NONE, line);
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return name + "(" + Arrays.toString(arguments.toArray()) + ")";
    }

    @Override
    public boolean isConstant() {
        return false;
    }
}
