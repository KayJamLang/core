package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.LexerException;
import com.github.kayjamlang.core.expressions.CastExpression;
import com.github.kayjamlang.core.expressions.Const;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CastExpressionTest {
    private static KayJamParser parser;

    @BeforeClass
    public static void prepare() {
        parser = new KayJamParser(new KayJamLexer("123 as int"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();
        assertNotNull(expression);
        assertEquals(CastExpression.class, expression.getClass());

        CastExpression castExpression = (CastExpression) expression;
        assertEquals(Const.class, castExpression.expression.getClass());
        assertEquals(Type.INTEGER, castExpression.castType);
    }
}
