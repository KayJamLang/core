package com.github.kayjamlang.core.statements;

import com.github.kayjamlang.core.expressions.data.Annotation;
import com.github.kayjamlang.core.expressions.data.Constant;
import com.github.kayjamlang.core.expressions.data.Property;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.List;

/**
 * Object class expression group
 */
public class ObjectStatement extends ClassStatement {

    /**
     * Creates anonymous object
     *
     * @param accessType Type of access
     * @param line       Line of object
     */
    public ObjectStatement(
            String name,
            String extendsClass,
            List<String> implementsClass,
            List<Constant> constants,
            List<Property> properties,
            List<FunctionStatement> functions,
            List<Annotation> annotations,
            AccessType accessType,
            int line
    ) {
        super(
                name,
                extendsClass, implementsClass,
                List.of(),
                constants,
                properties,
                functions,
                annotations,
                accessType,
                line
        );
    }
}
