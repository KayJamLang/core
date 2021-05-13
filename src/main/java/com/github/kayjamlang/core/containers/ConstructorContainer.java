package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.List;

/**
 * Constructor of class
 */
public class ConstructorContainer extends Container {

    /**
     * Constructor arguments
     * Can be empty
     */
    public final List<Argument> arguments;

    /**
     * Create constructor for class
     * @param arguments Arguments of constructor
     * @param children Code in constructor
     * @param accessType Type of access
     * @param line Line of constructor
     */
    public ConstructorContainer(List<Argument> arguments, List<Expression> children,
                                AccessIdentifier accessType, int line) {
        super(children, accessType, line);
        this.arguments = arguments;
    }
}
