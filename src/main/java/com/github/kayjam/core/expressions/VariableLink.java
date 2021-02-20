package com.github.kayjam.core.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.Type;
import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.exceptions.runtime.NotFoundException;
import com.github.kayjam.core.opcodes.AccessIdentifier;
import com.github.kayjam.core.provider.Context;

import java.util.ArrayList;

public class VariableLink extends Expression {
    public final String name;

    public VariableLink(String name, int line) {
        super(AccessIdentifier.NONE, line);
        this.name = name;
    }

    @Override
    public String toString() {
        return "VariableLink{" +
                "name='" + name + '\'' +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}
