package com.github.kayjamlang.core.exceptions;

import com.github.kayjamlang.core.KayJamLexer;

public class ParserException extends Exception {
    public ParserException(KayJamLexer lexer, String message){
        super(message+" in "+lexer.getLine()+" line");
    }

    public ParserException(int line, String message){
        super(message+" in "+line+" line");
    }
}

