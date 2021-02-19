package com.github.kayjam.core.containers;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.opcodes.AccessIdentifier;

import java.util.List;

public class ConstructorContainer extends Container {

    public final List<String> arguments;

    public ConstructorContainer(List<String> arguments, List<Expression> children,
                                AccessIdentifier identifier, int line) {
        super(children, identifier, line);
        this.arguments = arguments;
    }
}
