package com.github.kayjam.core.expressions;

public class Annotation {
    public final String name;
    public final Const value;

    public Annotation(String name) {
        this.name = name;
        this.value = null;
    }

    public Annotation(String name, Const value) {
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
