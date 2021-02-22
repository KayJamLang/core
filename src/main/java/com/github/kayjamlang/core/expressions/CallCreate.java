package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjam.core.containers.*;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;
import com.github.kayjamlang.core.containers.ClassContainer;

import java.util.ArrayList;
import java.util.List;

public class CallCreate extends Expression {

    public final String functionName;
    public final List<Expression> arguments;

    public CallCreate(String functionName, List<Expression> arguments, int line){
        super(AccessIdentifier.NONE, line);
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public Object execute(Container parent, Container argsParent) throws Exception {
        List<Object> arguments = new ArrayList<>();
        for (Expression argument : this.arguments)
            arguments.add(argument.execute(parent, argsParent));

        if(parent.variables.containsKey(functionName)){
            Object value = parent.variables.get(functionName);
            if(value instanceof FunctionRef){
                FunctionRef function = (FunctionRef) value;
                if(function.accept(arguments)) {
                    return function.call(arguments);
                }
            }
        }

        if(parent.classes.containsKey(functionName)){
            ClassContainer container = parent.classes.get(functionName);

            ClassContainer.Class clazz = new ClassContainer.Class(container, line);
            clazz.mainContainer = argsParent.mainContainer;
            return clazz.execute(container, argsParent);
        }else{
            Function function = parent.findFunction(functionName, arguments);
            return function.call(parent, argsParent, arguments);
        }
    }

    @Override
    public String toString() {
        return "FunctionCall{" +
                "functionName='" + functionName + '\'' +
                ", arguments=" + arguments +
                ", identifier=" + identifier +
                ", line=" + line +
                '}';
    }
}
