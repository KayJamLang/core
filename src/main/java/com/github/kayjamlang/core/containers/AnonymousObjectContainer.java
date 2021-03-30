package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.List;

public class AnonymousObjectContainer extends ObjectContainer {

    public AnonymousObjectContainer(List<Expression> children,
                                    AccessIdentifier identifier,
                                    int line) throws Exception {
        super("anonymous@class", children, identifier, line);
    }
}
