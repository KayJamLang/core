package com.github.kayjamlang.backend.jvm;

import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.parser.KayJamParser;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.expressions.Expression;

public class TestsUtils {

    public static Expression parse(String code) throws KayJamParserException, KayJamLexerException {
        return getParser(code).readExpression();
    }

    public static KayJamParser getParser(String code) {
        return new KayJamParser(new KayJamFile("test"), new KayJamLexer(code));
    }
}
