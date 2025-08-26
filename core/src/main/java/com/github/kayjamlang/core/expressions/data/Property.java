package com.github.kayjamlang.core.expressions.data;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.List;

public class Property {
    /**
     * Type of access
     */
    public final AccessType accessType;

    /**
     * Annotations of function
     * Can be empty
     */
    public final List<Annotation> annotations;

    /**
     * Name of constant
     */
    public final String name;

    /**
     * Value of constant
     */
    public final Expression value;

    public Property(
            String name,
            Expression value,
            List<Annotation> annotations,
            AccessType accessType
    ) {
        this.name = name;
        this.value = value;
        this.annotations = annotations;
        this.accessType = accessType;
    }
}