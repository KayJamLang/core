package com.github.kayjamlang.core.statements;

import com.github.kayjamlang.core.expressions.data.Annotation;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.List;

/**
 * Constructor of class
 */
public class ConstructorStatement extends Statement {
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
     * Constructor arguments
     * Can be empty
     */
    public final List<Argument> arguments;

    /**
     * Statements in container
     */
    public final List<Statement> children;

    /**
     * Create constructor for class
     *
     * @param arguments   Arguments of constructor
     * @param children    Code in constructor
     * @param accessType  Type of access
     * @param annotations List of annotations
     * @param line        Line of constructor
     */
    public ConstructorStatement(
            List<Argument> arguments,
            List<Statement> children,
            AccessType accessType,
            List<Annotation> annotations,
            int line
    ) {
        super(line);
        this.arguments = arguments;
        this.children = children;
        this.accessType = accessType;
        this.annotations = annotations;
    }
}
