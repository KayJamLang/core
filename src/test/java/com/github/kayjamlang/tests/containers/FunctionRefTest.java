package com.github.kayjamlang.tests.containers;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.FunctionRefExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionRefTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("-> (test) true");

        assertNotNull(expression);
        assertSame(FunctionRefExpression.class, expression.getClass());

        FunctionRefExpression functionRefExpression = (FunctionRefExpression) expression;
        assertEquals(1, functionRefExpression.arguments.size());
        assertEquals("test", functionRefExpression.arguments.get(0).name);
        assertEquals(Type.ANY, functionRefExpression.arguments.get(0).type);
        assertSame(ValueExpression.class, functionRefExpression.expression.getClass());

        ValueExpression constant = (ValueExpression) functionRefExpression.expression;
        assertEquals(true, constant.value);
    }

    @Test
    public void zeroArguments() throws Exception {
        Expression expression = TestsUtils.parse("-> true");

        assertNotNull(expression);
        assertSame(FunctionRefExpression.class, expression.getClass());

        FunctionRefExpression functionRefExpression = (FunctionRefExpression) expression;
        assertEquals(0, functionRefExpression.arguments.size());
        assertSame(ValueExpression.class, functionRefExpression.expression.getClass());

        ValueExpression constant = (ValueExpression) functionRefExpression.expression;
        assertEquals(true, constant.value);
    }
}