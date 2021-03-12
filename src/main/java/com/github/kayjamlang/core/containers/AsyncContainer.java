package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.List;

@Deprecated
public class AsyncContainer extends Container {
    public AsyncContainer(List<Expression> children, int line) {
        super(children, AccessIdentifier.NONE, line);
    }
}
