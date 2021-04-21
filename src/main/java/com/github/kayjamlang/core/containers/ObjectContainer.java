package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.Collections;
import java.util.List;

public class ObjectContainer extends ClassContainer {
    public ObjectContainer(List<Expression> children,
                           AccessIdentifier identifier,
                           int line) throws ParserException {
        super("anonymous@class", null,
                Collections.emptyList(),
                children, identifier, line);

        verify();
    }

    public ObjectContainer(String name,
                           List<Expression> children,
                           AccessIdentifier identifier,
                           int line) throws ParserException {
        super(name, null, Collections.emptyList(),
                children, identifier, line);
        verify();

        try {
            companion = (ObjectContainer) this.clone();
        }catch (Exception ignored){}

        variables.clear();
        functions.clear();
    }

    public void verify() throws ParserException {
        if(companion!=null)
            throw new ParserException(companion.line,
                    "Objects cannot have companions");

        if(constructors.size()!=0)
            throw new ParserException(constructors.get(0).line,
                    "Objects cannot have constructors");
    }
}
