package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Stmt;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Annotation;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.List;

/**
 * Function expression group
 */
public class FunctionContainer extends Container {
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
     * Annotations of function
     * Can be empty
     */
    public final List<Annotation> annotations;

    /**
     * Type of access
     */
    public final AccessType accessType;

    /**
     * Creates function
     * @param name Function name
     * @param children Expressions in function
     * @param accessType Type of access
     * @param arguments Function arguments
     * @param returnType Type of return
     * @param annotations List of annotations
     * @param line Function line
     */
    public FunctionContainer(String name, List<Stmt> children,
                             AccessType accessType, List<Argument> arguments,
                             Type returnType, List<Annotation> annotations, int line) {
        super(children, line);

        this.name = name;
        this.accessType = accessType;
        this.returnType = returnType;
        this.arguments = arguments;
        this.annotations = annotations;
    }
}

