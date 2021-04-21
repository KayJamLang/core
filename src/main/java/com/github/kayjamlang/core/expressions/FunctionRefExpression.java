package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.List;

public class FunctionRefExpression extends Expression {

    public final Expression expression;
    public final List<Argument> arguments;
    public final Type typeOfReturn;
    
    public FunctionRefExpression(List<Argument> arguments, Expression expression, Type typeOfReturn,
                                 int line) {
        super(AccessIdentifier.NONE, line);
        this.expression = expression;
        this.typeOfReturn = typeOfReturn;
        this.arguments = arguments;
    }
}
