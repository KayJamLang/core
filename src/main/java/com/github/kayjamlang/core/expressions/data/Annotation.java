package com.github.kayjamlang.core.expressions.data;

import com.github.kayjamlang.core.expressions.ValueExpression;

public class Annotation {
    public final String name;
    public final ValueExpression value;

    public Annotation(String name) {
        this.name = name;
        this.value = null;
    }

    public Annotation(String name, ValueExpression value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Annotation{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
