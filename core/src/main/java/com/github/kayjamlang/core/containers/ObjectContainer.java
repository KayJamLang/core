package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object class expression group
 */
public class ObjectContainer extends ClassContainer {
    public final boolean anonymous;

    public Map<String, Expression> constants = new HashMap<>();

    /**
     * Creates anonymous object
     * @param children Code in object
     * @param accessType Type of access
     * @param line Line of object
     * @throws KayJamParserException Unknown expression in code
     */
    public ObjectContainer(List<Expression> children,
                           AccessType accessType,
                           int line) throws KayJamParserException {
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
     * @throws KayJamParserException Unknown expression in code
     */
    public ObjectContainer(String name,
                           Map<String, Expression> constants,
                           List<Expression> children,
                           AccessType accessType,
                           int line) throws KayJamParserException {
        super(name, null, Collections.emptyList(),
                children, accessType, line);
        this.constants = constants;
        anonymous = false;
        verify();

        try {
            companion = (ObjectContainer) this.clone();
        }catch (CloneNotSupportedException ignored){}

        variables.clear();
        functions.clear();
    }

    /**
     * Checks data of object
     * @throws KayJamParserException Unknown expression in code
     */
    private void verify() throws KayJamParserException {
        if(companion!=null)
            throw new KayJamParserException(companion.line,
                    "Objects cannot have companions");

        if(constructors.size()!=0)
            throw new KayJamParserException(constructors.get(0).line,
                    "Objects cannot have constructors");
    }
}
