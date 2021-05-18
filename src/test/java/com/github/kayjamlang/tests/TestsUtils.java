package com.github.kayjamlang.tests;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.exceptions.LexerException;
import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.expressions.Expression;

public class TestsUtils {

    public static Expression parse(String code) throws ParserException, LexerException {
        return getParser(code).readExpression();
    }

    public static KayJamParser getParser(String code) {
        return new KayJamParser(new KayJamLexer(code));
    }
}
