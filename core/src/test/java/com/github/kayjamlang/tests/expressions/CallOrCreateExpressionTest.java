package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.ContainerExpression;
import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class CallOrCreateExpressionTest {

    @Test
    public void oneArgument() throws Exception {
        Expression expression = TestsUtils.parseExpression("concat(2021)");

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
        Expression expression = TestsUtils.parseExpression("concat(2021,\"year\")");

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
        Expression expression = TestsUtils.parseExpression("concat()");

        assertNotNull(expression);
        assertSame(CallOrCreateExpression.class, expression.getClass());

        CallOrCreateExpression callOrCreateExpression = (CallOrCreateExpression) expression;
        assertEquals("concat", callOrCreateExpression.name);
        assertEquals(0, callOrCreateExpression.arguments.size());
    }

    @Test
    public void lambdaArgument() throws Exception {
        Expression expression = TestsUtils.parseExpression("thread -> 1");

        assertNotNull(expression);
        assertSame(CallOrCreateExpression.class, expression.getClass());

        CallOrCreateExpression callOrCreateExpression = (CallOrCreateExpression) expression;
        assertEquals("thread", callOrCreateExpression.name);
        assertEquals(1, callOrCreateExpression.arguments.size());

        Expression firstArgument = callOrCreateExpression.arguments.get(0);
        assertSame(FunctionRefExpression.class, firstArgument.getClass());

        FunctionRefExpression ref = (FunctionRefExpression) firstArgument;
        assertEquals(ValueExpression.class, ref.expression.getClass());
    }

    @Test
    public void inAccessShortLambdaArgument() throws Exception {
        Expression expression = TestsUtils.parseExpression("threading.thread {}");

        assertNotNull(expression);
        assertSame(AccessExpression.class, expression.getClass());

        AccessExpression accessExpression = (AccessExpression) expression;
        assertSame(VariableLinkExpression.class, accessExpression.root.getClass());
        assertSame(CallOrCreateExpression.class, accessExpression.child.getClass());

        CallOrCreateExpression callOrCreateExpression = (CallOrCreateExpression) accessExpression.child;
        assertEquals("thread", callOrCreateExpression.name);
        assertEquals(1, callOrCreateExpression.arguments.size());

        Expression firstArgument = callOrCreateExpression.arguments.get(0);
        assertSame(FunctionRefExpression.class, firstArgument.getClass());

        FunctionRefExpression ref = (FunctionRefExpression) firstArgument;
        assertEquals(ContainerExpression.class, ref.expression.getClass());
    }
}