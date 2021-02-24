package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.exceptions.runtime.NotFoundException;
import com.github.kayjamlang.core.expressions.If;
import com.github.kayjamlang.core.expressions.Return;
import com.github.kayjamlang.core.expressions.VariableLink;
import com.github.kayjamlang.core.expressions.VariableSet;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container extends Expression {
    public final List<Expression> children = new ArrayList<>();
    public final List<Function> functions = new ArrayList<>();

    @Deprecated
    public final Map<String, ClassContainer> classes = new HashMap<>();

    public Container(List<Expression> children, AccessIdentifier identifier, int line) {
        super(identifier, line);

        for(Expression expression: children){
            if(expression instanceof Function)
                functions.add((Function) expression);
            else this.children.add(expression);
        }
    }

    @Override
    public String toString() {
        return "Container{" +
                "children=" + children +
                ", functions=" + functions +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}
