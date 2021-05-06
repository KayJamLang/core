package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.UseExpression;
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
        Expression expression = parser.readTopExpression();

        assertNotNull(expression);
        assertSame(UseExpression.class, expression.getClass());

        UseExpression useExpression = (UseExpression) expression;
        assertSame(ValueExpression.class, useExpression.expression.getClass());

        ValueExpression ifTrue = (ValueExpression) useExpression.expression;
        assertEquals(12345, ifTrue.value);
    }
}