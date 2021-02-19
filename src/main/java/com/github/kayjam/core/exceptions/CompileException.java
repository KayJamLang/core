package com.github.kayjam.core.exceptions;

import com.github.kayjam.core.KayJamLexer;

public class CompileException extends Exception {
    public CompileException(KayJamLexer lexer, String message){
        super(message+" in "+lexer.getLine()+" line");
    }

    public CompileException(int line, String message){
        super(message+" in "+line+" line");
    }
}

