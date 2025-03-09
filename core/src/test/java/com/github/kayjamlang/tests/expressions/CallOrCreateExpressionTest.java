package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.CallOrCreateExpression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CallOrCreateExpressionTest {

    @Test
    public void oneArgument() throws Exception {
        Expression expression = TestsUtils.parse("concat(2021)");

        assertNotNull(expression);
        assertSame(CallOrCreateExpression.class, expression.getClass());

        CallOrCreateExpression callOrCreateExpression = (CallOrCreateExpression) expression;
        assertEquals("concat", callOrCreateExpression.name);
        assertEquals(1, callOrCreateExpression.arguments.size());

        Expression firstArgument = callOrCreateExpression.arguments.get(0);
        assertSame(ValueExpression.class, firstArgument.getClass());

        ValueExpression firstArgumentConstant = (ValueExpression) firstArgument;
        assertEquals(2021, firstArgumentConstant.value);
    }

    @Test
    public void twoArgs() throws Exception {
        Expression expression = TestsUtils.parse("concat(2021,\"year\")");

        assertNotNull(expression);
        assertSame(CallOrCreateExpression.class, expression.getClass());

        CallOrCreateExpression callOrCreateExpression = (CallOrCreateExpression) expression;
        assertEquals("concat", callOrCreateExpression.name);
        assertEquals(2, callOrCreateExpression.arguments.size());

        Expression firstArgument = callOrCreateExpression.arguments.get(0);
        assertSame(ValueExpression.class, firstArgument.getClass());

        ValueExpression firstArgumentConstant = (ValueExpression) firstArgument;
        assertEquals(2021, firstArgumentConstant.value);

        Expression secondaryArgument = callOrCreateExpression.arguments.get(1);
        assertSame(ValueExpression.class, secondaryArgument.getClass());

        ValueExpression secondaryArgumentConstant = (ValueExpression) secondaryArgument;
        assertEquals("year", secondaryArgumentConstant.value);
    }


    @Test
    public void noArguments() throws Exception {
        Expression expression = TestsUtils.parse("concat()");

        assertNotNull(expression);
        assertSame(CallOrCreateExpression.class, expression.getClass());

        CallOrCreateExpression callOrCreateExpression = (CallOrCreateExpression) expression;
        assertEquals("concat", callOrCreateExpression.name);
        assertEquals(0, callOrCreateExpression.arguments.size());
    }
}