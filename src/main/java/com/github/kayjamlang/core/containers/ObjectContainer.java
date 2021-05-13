package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.Collections;
import java.util.List;

/**
 * Object class expression group
 */
public class ObjectContainer extends ClassContainer {
    public final boolean anonymous;

    /**
     * Creates anonymous object
     * @param children Code in object
     * @param accessType Type of access
     * @param line Line of object
     * @throws ParserException Unknown expression in code
     */
    public ObjectContainer(List<Expression> children,
                           AccessType accessType,
                           int line) throws ParserException {
        super("anonymous@class", null,
                Collections.emptyList(),
                children, accessType, line);
        anonymous = true;

        verify();
    }

    /**
     * Creates class object
     * @param name Name of object
     * @param children Code in object
     * @param accessType Type of access
     * @param line Line of object
     * @throws ParserException Unknown expression in code
     */
    public ObjectContainer(String name,
                           List<Expression> children,
                           AccessType accessType,
                           int line) throws ParserException {
        super(name, null, Collections.emptyList(),
                children, accessType, line);
        anonymous = false;
        verify();

        try {
            companion = (ObjectContainer) this.clone();
        }catch (Exception ignored){}

        variables.clear();
        functions.clear();
    }

    /**
     * Checks data of object
     * @throws ParserException Unknown expression in code
     */
    private void verify() throws ParserException {
        if(companion!=null)
            throw new ParserException(companion.line,
                    "Objects cannot have companions");

        if(constructors.size()!=0)
            throw new ParserException(constructors.get(0).line,
                    "Objects cannot have constructors");
    }
}
