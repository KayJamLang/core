package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Stmt;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.Collections;
import java.util.List;

/**
 * Named function
 */
public class NamedExpressionFunctionContainer extends FunctionContainer {

    /**
     * @param name Name of function
     * @param children Code of function
     * @param accessType Type of access
     * @param line Start of named function
     */
    public NamedExpressionFunctionContainer(String name,
                                            List<Stmt> children,
                                            AccessType accessType,
                                            int line) {
        super(name, children, accessType,
                Collections.singletonList(new Argument(Type.FUNCTION_REF, name)),
                Type.VOID, Collections.emptyList(), line);
    }
}
