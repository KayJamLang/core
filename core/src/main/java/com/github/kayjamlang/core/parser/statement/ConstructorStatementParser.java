package com.github.kayjamlang.core.parser.statement;

import com.github.kayjamlang.core.Token;
import com.github.kayjamlang.core.statements.ConstructorStatement;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.expressions.data.Annotation;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.opcodes.AccessType;
import com.github.kayjamlang.core.parser.KayJamParser;

import java.util.List;

public class ConstructorStatementParser {
    public static ConstructorStatement parse(
            List<Annotation> annotations,
            AccessType accessType,
            KayJamParser parser
    ) throws KayJamLexerException, KayJamParserException {
        int line = parser.lexer.getLine();
        parser.requireToken(Token.Type.TK_OPEN);
        List<Argument> arguments = parser.parseArguments();

        parser.moveAhead();
        return new ConstructorStatement(arguments, parser.parseStatements(), accessType, annotations, line);
    }
}
