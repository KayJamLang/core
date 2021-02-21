package com.github.kayjam.tests;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;
import com.github.kayjam.core.expressions.Const;
import com.github.kayjam.core.expressions.Not;
import com.github.kayjam.core.expressions.Return;
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