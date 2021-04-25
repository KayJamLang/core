package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.AssertNullExpression;
import com.github.kayjamlang.core.expressions.CallOrCreateExpression;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AssertNullExpressionInFunctionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("test(123!)"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(CallOrCreateExpression.class, expression.getClass());

        CallOrCreateExpression callOrCreateExpression = (CallOrCreateExpression) expression;
        assertEquals(1, callOrCreateExpression.arguments.size());
        assertSame(AssertNullExpression.class, callOrCreateExpression.arguments.get(0).getClass());

        AssertNullExpression assertNull = (AssertNullExpression) callOrCreateExpression.arguments.get(0);
        assertSame(ValueExpression.class, assertNull.expression.getClass());
    }
}