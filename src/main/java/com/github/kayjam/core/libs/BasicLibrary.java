package com.github.kayjam.core.libs;

import com.github.kayjam.core.containers.MainContainer;

public class BasicLibrary {
    @MainContainer.KayJamFunction
    public static void print(MainContainer mainContainer, Object value){
        Output output = (Output) mainContainer.data.get("output");
        output.print(value.toString());
    }

    @MainContainer.KayJamFunction
    public static void println(MainContainer mainContainer, Object value){
        Output output = (Output) mainContainer.data.get("output");
        output.println(value.toString());
    }

    public interface Output{
        void print(String value);
        void println(String value);
    }
}
