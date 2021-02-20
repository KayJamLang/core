package com.github.kayjam.core.containers;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.exceptions.runtime.NotFoundException;
import com.github.kayjam.core.expressions.If;
import com.github.kayjam.core.expressions.Return;
import com.github.kayjam.core.expressions.VariableLink;
import com.github.kayjam.core.expressions.VariableSet;
import com.github.kayjam.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container extends Expression {
    public MainContainer mainContainer;

    public final List<Expression> children = new ArrayList<>();
    public final List<Function> functions = new ArrayList<>();

    @Deprecated
    public final Map<String, ClassContainer> classes = new HashMap<>();

    @Deprecated
    public final Map<String, Object> variables = new HashMap<>();

    public boolean globalVariables = true;
    public boolean globalFunctions = true;

    public Container(List<Expression> children, AccessIdentifier identifier, int line) {
        super(identifier, line);

        for(Expression expression: children){
            if(expression instanceof Function)
                functions.add((Function) expression);
            else this.children.add(expression);
        }
    }

    @Deprecated
    public Function findFunction(String name, List<Object> arguments) throws Exception {
        for(Function function: functions)
            if(function.accept(name, arguments))
                return function;

        for(Function function: mainContainer.functions)
            if(function.accept(name, arguments))
                return function;

        throw new NotFoundException(this, "function", name);
    }

    public Object onExecute(Container parent) throws Exception {
        Object result = 0;
        for(Expression child: children){
            if(child instanceof VariableSet){
                VariableSet set = (VariableSet) child;
                if(!variables.containsKey(set.name)){
                    set.execute(parent);
                }else set.execute(this);
            }else{
                Object value = child.execute(this);
                if (child instanceof Return)
                    return value;
                else if (value != null)
                    if (child instanceof Container||
                            child instanceof If||
                            child instanceof VariableLink)
                        return value;
            }
        }

        return result;
    }

    @Override
    public final Object execute(Container parent, Container argsParent) throws Exception {
        mainContainer = argsParent.mainContainer;

        classes.putAll(argsParent.classes);
        variables.put("this", this);
        if(globalVariables)
            variables.putAll(argsParent.variables);

        if(globalFunctions&&!argsParent.equals(this)) {
            for(Function function: argsParent.functions)
                if(function.identifier!=AccessIdentifier.PRIVATE)
                    functions.addAll(argsParent.functions);
        }

        return onExecute(parent);
    }

    @Override
    public String toString() {
        return "Container{" +
                "children=" + children +
                ", functions=" + functions +
                ", variables=" + variables +
                ", globalVariables=" + globalVariables +
                ", globalFunctions=" + globalFunctions +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}
