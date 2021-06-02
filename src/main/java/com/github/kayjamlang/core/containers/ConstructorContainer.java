package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Stmt;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.Collections;
import java.util.List;

/**
 * Constructor of class
 */
public class ConstructorContainer extends FunctionContainer {

    /**
     * Create constructor for class
     * @param arguments Arguments of constructor
     * @param children Code in constructor
     * @param line Line of constructor
     */
    public ConstructorContainer(List<Argument> arguments, List<Stmt> children, int line) {
        super(null, children, AccessType.NONE, arguments, Type.VOID, Collections.emptyList(), line);
    }
}
