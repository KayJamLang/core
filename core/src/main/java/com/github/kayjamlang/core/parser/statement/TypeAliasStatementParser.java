package com.github.kayjamlang.core.parser.statement;

import com.github.kayjamlang.core.Token;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.expressions.data.Annotation;
import com.github.kayjamlang.core.opcodes.AccessType;
import com.github.kayjamlang.core.parser.KayJamParser;
import com.github.kayjamlang.core.statements.TypeAliasStatement;

import java.util.List;

public class TypeAliasStatementParser {
    public static TypeAliasStatement parse(
            List<Annotation> annotations,
            AccessType accessType,
            KayJamParser parser
    ) throws KayJamLexerException, KayJamParserException {
        int line = parser.lexer.getLine();
        String aliasName = parser.requireToken(Token.Type.IDENTIFIER).value;
        parser.requireToken(Token.Type.TK_ASSIGN);
        Type type = parser.parseType(false);

        return new TypeAliasStatement(
                aliasName,
                type,
                annotations,
                accessType,
                line
        );
    }
}
