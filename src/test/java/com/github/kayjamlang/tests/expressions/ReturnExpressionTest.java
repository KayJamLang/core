package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.expressions.Return;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReturnExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("return 2005"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(Return.class, expression.getClass());

        Return returnExpression = (Return) expression;
        assertSame(Const.class, returnExpression.expression.getClass());

        Const constant = (Const) returnExpression.expression;
        assertEquals(2005, constant.value);
    }
}