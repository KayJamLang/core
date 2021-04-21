package com.github.kayjamlang.core.expressions.data;

import com.github.kayjamlang.core.Type;

public class Argument {
    public final Type type;
    public final String name;

    public Argument(Type type, String name) {
        this.type = type;
        this.name = name;
    }
}
