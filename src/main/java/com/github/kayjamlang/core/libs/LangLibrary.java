package com.github.kayjamlang.core.libs;

import com.github.kayjamlang.core.containers.MainContainer;

public class LangLibrary {

    @MainContainer.KayJamFunction
    public static Object concat(MainContainer mainContainer, Object left, Object right){
        if(left instanceof String||right instanceof String)
            return String.valueOf(left) + right;

        if(left instanceof Number&&right instanceof Number)
            return ((Number) left).doubleValue()+((Number) right).doubleValue();

        return null;
    }
}
