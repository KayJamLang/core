package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.expressions.Variable;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectContainer extends ClassContainer {
    public ObjectContainer(String name,
                           List<Expression> children,
                           AccessIdentifier identifier,
                           int line) throws Exception {
        super(name, null, Collections.emptyList(),
                children, identifier, line);
        if(companion!=null)
            throw new ParserException(companion.line, "Objects cannot have companions");
    }
}
