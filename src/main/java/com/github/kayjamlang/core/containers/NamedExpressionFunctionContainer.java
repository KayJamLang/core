package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NamedExpressionFunctionContainer extends FunctionContainer {
    public NamedExpressionFunctionContainer(String name,
                                            List<Expression> children,
                                            AccessIdentifier identifier,
                                            int line) {
        super(name, children, identifier,
                Collections.singletonList(new Argument(Type.FUNCTION_REF, name)),
                line, Type.VOID, new ArrayList<>());
    }
}
