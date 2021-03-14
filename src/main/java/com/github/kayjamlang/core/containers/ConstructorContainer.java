package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Argument;
import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.List;

public class ConstructorContainer extends Container {

    public final List<Argument> arguments;

    public ConstructorContainer(List<Argument> arguments, List<Expression> children,
                                AccessIdentifier identifier, int line) {
        super(children, identifier, line);
        this.arguments = arguments;
    }
}
