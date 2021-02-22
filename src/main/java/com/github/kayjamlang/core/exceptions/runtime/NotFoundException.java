package com.github.kayjamlang.core.exceptions.runtime;

import com.github.kayjamlang.core.Expression;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Expression expression, String type, String name) {
        super(expression, "Not found \""+name+"\" "+type);
    }

    public NotFoundException(Expression expression, String message) {
        super(expression, "Not found "+message);
    }
}
