package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.List;

/**
 * Lambda Expression
 */
public class FunctionRefExpression extends Expression {
    /**
     * c
     */
    public final Expression expression;

    /**
     * Lambda arguments
     */
    public final List<Argument> arguments;

    /**
     * Return lambda type
     * Default: any
     */
    public final Type typeOfReturn;

    /**
     * @param arguments Lambda arguments
     * @param expression Lambda arguments
     * @param typeOfReturn Return lambda type
     * @param line Start line
     */
    public FunctionRefExpression(List<Argument> arguments, Expression expression, Type typeOfReturn,
                                 int line) {
        super(AccessType.NONE, line);
        this.expression = expression;
        this.typeOfReturn = typeOfReturn;
        this.arguments = arguments;
    }
}
