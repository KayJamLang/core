package com.github.kayjamlang.core.parser.statement;

import com.github.kayjamlang.core.Token;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Property;
import com.github.kayjamlang.core.parser.data.PropertyParser;
import com.github.kayjamlang.core.statements.ConstructorStatement;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.expressions.data.Annotation;
import com.github.kayjamlang.core.expressions.data.Constant;
import com.github.kayjamlang.core.opcodes.AccessType;
import com.github.kayjamlang.core.parser.KayJamParser;
import com.github.kayjamlang.core.parser.KayJamParserKeywords;
import com.github.kayjamlang.core.parser.data.ConstantParser;
import com.github.kayjamlang.core.statements.ClassStatement;
import com.github.kayjamlang.core.statements.FunctionStatement;

import java.util.ArrayList;
import java.util.List;

public class ClassStatementParser {
    public static ClassStatement parse(
            List<Annotation> annotations,
            AccessType accessType,
            KayJamParser parser
    ) throws KayJamLexerException, KayJamParserException {
        int line = parser.lexer.getLine();
        String name = parser.requireToken(Token.Type.IDENTIFIER).value;
        parser.moveAhead();

        List<Type> parentClasses = new ArrayList<>();
        if (parser.currentTokenType() == Token.Type.TK_COLON) {
            do {
                parser.moveAhead();
                parentClasses.add(parser.parseType(false));
            } while (parser.currentTokenType() == Token.Type.TK_COMMA);
        }

        parser.requireToken(Token.Type.OPEN_BRACKET, false);
        parser.moveAhead();

        List<Constant> constants = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        List<ConstructorStatement> constructors = new ArrayList<>();
        List<FunctionStatement> functions = new ArrayList<>();

        while (parser.currentTokenType() != Token.Type.CLOSE_BRACKET) {
            while (parser.lexer.currentToken().type == Token.Type.TK_SEMI) {
                parser.moveAhead();
            }

            if (parser.lexer.currentToken().type == Token.Type.CLOSE_BRACKET) {
                break;
            }

            List<Annotation> statementAnnotations = parser.readAnnotations();
            AccessType statementAccessType = parser.readAccessType();

            switch (KayJamParserKeywords.find(parser.lexer.currentToken().value)) {
                case FUNCTION ->
                        functions.add(FunctionStatementParser.parse(statementAnnotations, statementAccessType, parser));
                case CONSTANT -> constants.add(ConstantParser.parse(statementAnnotations, statementAccessType, parser));
                case PROPERTY ->
                        properties.add(PropertyParser.parse(statementAnnotations, statementAccessType, parser));
                case CONSTRUCTOR ->
                        constructors.add(ConstructorStatementParser.parse(statementAnnotations, statementAccessType, parser));
                case null, default ->
                        throw new KayJamParserException(parser.lexer, "unexpected keyword: " + parser.lexer.currentToken().value);
            }
        }

        parser.moveAhead();
        return new ClassStatement(
                name,
                parentClasses,
                constructors,
                constants, properties,
                functions,
                annotations,
                accessType,
                line
        );
    }
}
