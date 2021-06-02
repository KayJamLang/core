package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Stmt;
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
    public final Stmt stmt;

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
     * @param stmt Lambda arguments
     * @param typeOfReturn Return lambda type
     * @param line Start line
     */
    public FunctionRefExpression(List<Argument> arguments, Stmt stmt, Type typeOfReturn,
                                 int line) {
        super(AccessType.NONE, line);
        this.stmt = stmt;
        this.typeOfReturn = typeOfReturn;
        this.arguments = arguments;
    }
}
