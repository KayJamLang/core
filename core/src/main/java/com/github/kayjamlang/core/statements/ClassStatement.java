package com.github.kayjamlang.core.statements;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Annotation;
import com.github.kayjamlang.core.expressions.data.Constant;
import com.github.kayjamlang.core.expressions.data.Property;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.List;

/**
 * Class expression group
 */
public class ClassStatement extends Statement {
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
     * Name of class
     */
    public final String name;

    /**
     * Class parents
     */
    public final List<Type> parentClasses;

    /**
     * Class constructors
     */
    public final List<ConstructorStatement> constructors;

    /**
     * Constants in class
     */
    public final List<Constant> constants;

    /**
     * Properties in class
     */
    public final List<Property> properties;

    /**
     * Functions in class
     */
    public final List<FunctionStatement> functions;

    /**
     * @param name Name of class
     * @param parentClasses Parents of class
     * @param properties Properties in class
     * @param accessType Access of class
     * @param line Line of start class
     */
    public ClassStatement(
            String name,
            List<Type> parentClasses,
            List<ConstructorStatement> constructors,
            List<Constant> constants,
            List<Property> properties,
            List<FunctionStatement> functions,
            List<Annotation> annotations,
            AccessType accessType,
            int line
    )  {
        super(line);
        this.name = name;
        this.parentClasses = parentClasses;
        this.accessType = accessType;
        this.functions = functions;
        this.constructors = constructors;
        this.constants = constants;
        this.properties = properties;
        this.annotations = annotations;
    }

    @Override
    public String toString() {
        return name+"@class";
    }
}
