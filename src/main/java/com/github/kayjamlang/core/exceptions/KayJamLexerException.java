package com.github.kayjamlang.core.exceptions;

import com.github.kayjamlang.core.KayJamLexer;

public class KayJamLexerException extends Exception {
    public KayJamLexerException(KayJamLexer lexer, String errorMessage) {
        super(errorMessage + " in " + lexer.source.path);
    }
}
