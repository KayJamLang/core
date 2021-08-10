package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.ReturnExpression;
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
        assertSame(ReturnExpression.class, expression.getClass());

        ReturnExpression returnExpression = (ReturnExpression) expression;
        assertSame(ValueExpression.class, returnExpression.expression.getClass());

        ValueExpression constant = (ValueExpression) returnExpression.expression;
        assertEquals(2005, constant.value);
    }
}