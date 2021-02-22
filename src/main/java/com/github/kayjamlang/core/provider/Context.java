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
    public final Map<String, Object> variables = new HashMap<>();

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
}
