package com.github.kayjamlang.core.expressions.data;

import com.github.kayjamlang.core.Type;

public class Argument {
    /**
     * Required type
     */
    public final Type type;

    /**
     * Name of argument
     */
    public final String name;

    /**
     * Creates argument
     * @param type Required type
     * @param name Name of argument
     */
    public Argument(Type type, String name) {
        this.type = type;
        this.name = name;
    }
}
