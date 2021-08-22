package com.github.kayjamlang.core.exceptions;

import com.github.kayjamlang.core.KayJamLexer;

public class KayJamParserException extends Exception {

    public KayJamParserException(KayJamLexer lexer, String message){
        super(message+" on "+lexer.getLine()+" line in "+lexer.source.path);
    }
    public KayJamParserException(int line, String message){
        super(message+" on "+line+" line");
    }
}

