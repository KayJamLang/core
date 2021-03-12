package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.List;

public class NamedContainer extends Container{
    public final String name;

    public NamedContainer(String name, List<Expression> children, int line) {
        super(children, AccessIdentifier.NONE, line);
        this.name = name;
    }
}
