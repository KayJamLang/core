package com.github.kayjamlang.core.statements;

import com.github.kayjamlang.core.exceptions.KayJamParserException;
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
     * Name of class parent
     */
    public final String extendsClass;

    /**
     * Classes implements
     */
    public final List<String> implementsClass;

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
     * @param extendsClass Name of class parent
     * @param implementsClass Classes implements
     * @param properties Properties in class
     * @param accessType Access of class
     * @param line Line of start class
     * @throws KayJamParserException throws on unexpected expression in code
     */
    public ClassStatement(
            String name,
            String extendsClass,
            List<String> implementsClass,
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
        this.extendsClass = extendsClass;
        this.implementsClass = implementsClass;
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
