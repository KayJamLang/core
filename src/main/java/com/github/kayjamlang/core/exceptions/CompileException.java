package com.github.kayjamlang.core.exceptions;

import com.github.kayjamlang.core.KayJamLexer;

public class CompileException extends Exception {
    public CompileException(KayJamLexer lexer, String message){
        super(message+" in "+lexer.getLine()+" line");
    }

    public CompileException(int line, String message){
        super(message+" in "+line+" line");
    }
}

