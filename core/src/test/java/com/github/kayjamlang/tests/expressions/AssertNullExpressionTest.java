package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.CallOrCreateExpression;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.AssertNullExpression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AssertNullExpressionTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("123!");

        assertNotNull(expression);
        assertSame(AssertNullExpression.class, expression.getClass());

        AssertNullExpression assertNull = (AssertNullExpression) expression;
        assertSame(ValueExpression.class, assertNull.expression.getClass());
    }

    @Test
    public void inFunction() throws Exception {
        Expression expression = TestsUtils.parse("test(123!)");

        assertNotNull(expression);
        assertSame(CallOrCreateExpression.class, expression.getClass());

        CallOrCreateExpression callOrCreateExpression = (CallOrCreateExpression) expression;
        assertEquals(1, callOrCreateExpression.arguments.size());
        assertSame(AssertNullExpression.class, callOrCreateExpression.arguments.get(0).getClass());

        AssertNullExpression assertNull = (AssertNullExpression) callOrCreateExpression.arguments.get(0);
        assertSame(ValueExpression.class, assertNull.expression.getClass());
    }
}