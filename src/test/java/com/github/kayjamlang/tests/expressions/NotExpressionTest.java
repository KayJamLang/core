package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.expressions.Not;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("!true"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(Not.class, expression.getClass());

        Not notExpression = (Not) expression;
        assertSame(Const.class, notExpression.expression.getClass());

        Const constant = (Const) notExpression.expression;
        assertEquals(true, constant.value);
    }
}