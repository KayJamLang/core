package com.github.kayjamlang.core.parser.data;

import com.github.kayjamlang.core.Token;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.data.Annotation;
import com.github.kayjamlang.core.expressions.data.Constant;
import com.github.kayjamlang.core.expressions.data.Property;
import com.github.kayjamlang.core.opcodes.AccessType;
import com.github.kayjamlang.core.parser.KayJamParser;

import java.util.List;

public class PropertyParser {
    public static Property parse(
            List<Annotation> annotations,
            AccessType accessType,
            KayJamParser parser
    ) throws KayJamLexerException, KayJamParserException {
        String name = parser.requireToken(Token.Type.IDENTIFIER).value;
        parser.requireToken(Token.Type.TK_ASSIGN);

        parser.moveAhead();
        Expression expression = parser.readExpression();

        return new Property(name, expression, annotations, accessType);
    }
}