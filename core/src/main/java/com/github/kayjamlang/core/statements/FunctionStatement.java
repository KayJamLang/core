package com.github.kayjamlang.core.statements;

import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Annotation;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.ArrayList;
import java.util.List;

/**
 * Function expression group
 */
public class FunctionStatement extends Statement {
    /**
     * Type of access
     */
    public final AccessType accessType;

    /**
     * Annotations of function
     * Can be empty
     */
    public final List<Annotation> annotations;

    /**
     * Function name
     */
    public final String name;

    /**
     * Function return type
     * Default type - void
     */
    public final Type returnType;

    /**
     * Arguments of function.
     * Can be empty
     */
    public final List<Argument> arguments;

    /**
     * Statements in container
     */
    public final List<Statement> children;

    /**
     * Creates function
     * @param name Function name
     * @param children Expressions in function
     * @param accessType Type of access
     * @param annotations List of annotations
     * @param arguments Function arguments
     * @param returnType Type of return
     * @param line Function line
     */
    public FunctionStatement(String name, List<Statement> children,
                             AccessType accessType, List<Argument> arguments,
                             Type returnType, List<Annotation> annotations, int line) {
        super(line);

        this.name = name;
        this.returnType = returnType;
        this.arguments = arguments;
        this.annotations = annotations;
        this.accessType = accessType;
        this.children = children;
    }
}

