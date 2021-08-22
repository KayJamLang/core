package com.github.kayjamlang.tests.containers;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.FunctionContainer;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class FunctionTests {
    @Test
    public void oneArgument() throws Exception {
        Expression expression = TestsUtils.parse("fun test(string value){true;false}");

        assertNotNull(expression);
        assertSame(FunctionContainer.class, expression.getClass());

        FunctionContainer functionContainer = (FunctionContainer) expression;
        assertEquals(1, functionContainer.arguments.size());
        assertEquals(2, functionContainer.children.size());

        Argument argument = functionContainer.arguments.get(0);
        assertEquals(Type.STRING, argument.type);
        assertEquals("value", argument.name);
    }

    @Test
    public void twoArgument() throws Exception {
        Expression expression = TestsUtils.parse("fun test(string value, int integer){true;false}");

        assertNotNull(expression);
        assertSame(FunctionContainer.class, expression.getClass());

        FunctionContainer functionContainer = (FunctionContainer) expression;
        assertEquals(2, functionContainer.arguments.size());
        assertEquals(2, functionContainer.children.size());

        Argument argument = functionContainer.arguments.get(0);
        assertEquals(Type.STRING, argument.type);
        assertEquals("value", argument.name);

        argument = functionContainer.arguments.get(1);
        assertEquals(Type.INTEGER, argument.type);
        assertEquals("integer", argument.name);
    }

    @Test
    public void noArgument() throws Exception {
        Expression expression = TestsUtils.parse("fun test(){true;false}");

        assertNotNull(expression);
        assertSame(FunctionContainer.class, expression.getClass());

        FunctionContainer functionContainer = (FunctionContainer) expression;
        assertEquals(0, functionContainer.arguments.size());
        assertEquals(2, functionContainer.children.size());
    }
}
