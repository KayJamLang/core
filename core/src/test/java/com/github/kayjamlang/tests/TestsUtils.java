package com.github.kayjamlang.tests;

import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.parser.KayJamParser;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.statements.Statement;

public class TestsUtils {

    public static Expression parseExpression(String code) throws KayJamParserException, KayJamLexerException {
        return getParser(code).readExpression();
    }

    public static Statement parseStatement(String code) throws KayJamParserException, KayJamLexerException {
        return getParser(code).readStatement();
    }

    public static KayJamParser getParser(String code) {
        return new KayJamParser(new KayJamFile("test"), new KayJamLexer(code));
    }
}
