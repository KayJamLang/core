package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.exceptions.ParserException;

public class Script extends PackContainer {

    public Script(Container container) throws ParserException {
        super("\\", container, true);
    }
}
