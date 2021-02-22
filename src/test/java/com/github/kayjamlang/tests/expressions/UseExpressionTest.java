package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.expressions.Use;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UseExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("use 12345"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(Use.class, expression.getClass());

        Use useExpression = (Use) expression;
        assertSame(Const.class, useExpression.expression.getClass());

        Const ifTrue = (Const) useExpression.expression;
        assertEquals(12345L, ifTrue.value);
    }
}