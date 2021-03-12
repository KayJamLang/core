package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NamedExpressionFunction extends Function {
    public NamedExpressionFunction(String name,
                                   List<Expression> children,
                                   AccessIdentifier identifier,
                                   int line) {
        super(name, children, identifier,
                Collections.singletonList(new Argument(Type.FUNCTION_REF, name)),
                line, Type.VOID, new ArrayList<>());
    }
}
