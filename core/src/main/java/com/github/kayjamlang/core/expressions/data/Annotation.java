package com.github.kayjamlang.core.expressions.data;

import com.github.kayjamlang.core.expressions.ValueExpression;

public class Annotation {
    /**
     * Annotation name
     */
    public final String name;

    /**
     * Value in annotation (can be null)
     */
    public final ValueExpression value;

    /**
     * Creates annotation without value
     * @param name Name of annotation
     */
    public Annotation(String name) {
        this.name = name;
        this.value = null;
    }

    /**
     * Creates annotation with value
     * @param name Name of annotation
     * @param value Value in annotation
     */
    public Annotation(String name, ValueExpression value) {
        this.name = name;
        this.value = value;
    }
}
