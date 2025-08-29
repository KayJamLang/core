package com.github.kayjamlang.core.parser.statement;

import com.github.kayjamlang.core.Token;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.expressions.data.Annotation;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.opcodes.AccessType;
import com.github.kayjamlang.core.parser.KayJamParser;
import com.github.kayjamlang.core.statements.FunctionStatement;
import com.github.kayjamlang.core.statements.Statement;

import java.util.List;

public class FunctionStatementParser {
    public static FunctionStatement parse(
            List<Annotation> annotations,
            AccessType accessType,
            KayJamParser parser
    ) throws KayJamLexerException, KayJamParserException {
        int line = parser.lexer.getLine();
        String name = parser.requireToken(Token.Type.IDENTIFIER).value;

        parser.requireToken(Token.Type.TK_OPEN);

        List<Argument> arguments = parser.parseArguments();

        Type returnType = Type.VOID;
        if (parser.moveAhead().type == Token.Type.TK_COLON) {
            parser.requireToken(Token.Type.IDENTIFIER);

            returnType = parser.parseType(true);
        }

        List<Statement> body = parser.parseStatements();
        return new FunctionStatement(name, body, accessType, arguments, returnType, annotations, line);
    }
}
