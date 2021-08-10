package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.AssertNullExpression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class AssertNullExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("123!"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(AssertNullExpression.class, expression.getClass());

        AssertNullExpression assertNull = (AssertNullExpression) expression;
        assertSame(ValueExpression.class, assertNull.expression.getClass());
    }
}