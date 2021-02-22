package com.github.kayjamlang.core.exceptions.runtime;

import com.github.kayjamlang.core.Expression;

public class RuntimeException extends Exception {
    public RuntimeException(Expression expression, String message){
        super(message+" in "+expression.line+" line");
    }
}
