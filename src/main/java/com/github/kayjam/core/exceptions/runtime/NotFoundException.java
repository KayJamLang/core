package com.github.kayjam.core.exceptions.runtime;

import com.github.kayjam.core.Expression;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Expression expression, String type, String name) {
        super(expression, "Not found \""+name+"\" "+type);
    }

    public NotFoundException(Expression expression, String message) {
        super(expression, "Not found "+message);
    }
}
