package com.github.kayjamlang.core.exceptions;

import com.github.kayjamlang.core.KayJamLexer;

public class KayJamLexerException extends Exception {
    public KayJamLexerException(KayJamLexer lexer, String errorMessage) {
        super("Lexer error: "+ errorMessage + " on "+lexer.getLine()+" in " + lexer.source.path);
    }
}
