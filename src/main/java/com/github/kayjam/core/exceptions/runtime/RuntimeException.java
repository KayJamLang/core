package com.github.kayjam.core.exceptions.runtime;

import com.github.kayjam.core.Expression;

public class RuntimeException extends Exception {
    public RuntimeException(Expression expression, String message){
        super(message+" in "+expression.line+" line");
    }
}
