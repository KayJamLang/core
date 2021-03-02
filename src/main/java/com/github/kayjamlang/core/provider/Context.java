package com.github.kayjamlang.core.provider;

import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.containers.Function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    public final Container parent;
    public final Context parentContext;
    private final Map<String, Object> variables = new HashMap<>();

    public Context(Container parent, Context parentContext, boolean importVars) {
        this.parent = parent;
        this.parentContext = parentContext;

        if(parentContext!=null&&importVars)
            variables.putAll(parentContext.variables);
    }

    public Context(Container parent, Context parentContext) {
        this.parent = parent;
        this.parentContext = parentContext;
    }

    public List<Function> findFunctions(String name){
        List<Function> functionsFound = new ArrayList<>();
        for(Function function: parent.functions)
            if(function.name.equals(name))
                functionsFound.add(function);

        if(parentContext!=null)
            functionsFound.addAll(parentContext.findFunctions(name));

        return functionsFound;
    }

    public boolean createVariable(String name, Object value){
        if(variables.containsKey(name)||parentContext!=null&&
                parentContext.variables.containsKey(name))
            return false;

        variables.put(name, value);
        return true;
    }

    public boolean setVariable(String name, Object value){
        if(!variables.containsKey(name)&&parentContext!=null&&
                !parentContext.variables.containsKey(name))
            return false;

        if(parentContext!=null&&parentContext.variables.containsKey(name))
            parentContext.variables.put(name, value);
        else variables.put(name, value);

        return true;
    }

    public Object getVariable(String name){
        if(parentContext!=null&&parentContext.variables.containsKey(name))
            return parentContext.variables.get(name);
        else if(variables.containsKey(name))
            return variables.get(name);

        return false;
    }
}
