package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.OperationExpression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValueExpressionTest {

    @Test
    public void doubleValue() throws Exception {
        Expression expression = TestsUtils.parse("12.4");

        assertNotNull(expression);
        assertSame(ValueExpression.class, expression.getClass());

        ValueExpression constant = (ValueExpression) expression;
        assertEquals(12.4, constant.value);
    }

    @Test
    public void intValue() throws Exception {
        Expression expression = TestsUtils.parse("12345");

        assertNotNull(expression);
        assertSame(ValueExpression.class, expression.getClass());

        ValueExpression constant = (ValueExpression) expression;
        assertEquals(12345, constant.value);
    }

    @Test
    public void longValue() throws Exception {
        Expression expression = TestsUtils.parse("123L");

        assertNotNull(expression);
        assertSame(ValueExpression.class, expression.getClass());

        ValueExpression constant = (ValueExpression) expression;
        assertEquals(123L, constant.value);
    }

    @Test
    public void negativeNumberValue() throws Exception {
        Expression expression = TestsUtils.parse("-12345");

        assertNotNull(expression);
        assertSame(OperationExpression.class, expression.getClass());

        OperationExpression operationExpression = (OperationExpression) expression;
        assertSame(ValueExpression.class, operationExpression.left.getClass());
        assertSame(ValueExpression.class, operationExpression.right.getClass());

        ValueExpression constant = (ValueExpression) operationExpression.right;
        assertEquals(12345, constant.value);

        constant = (ValueExpression) operationExpression.left;
        assertEquals(-1, constant.value);
    }

    @Test
    public void stringValue() throws Exception {
        Expression expression = TestsUtils.parse("\"test\"");

        assertNotNull(expression);
        assertSame(ValueExpression.class, expression.getClass());

        ValueExpression constant = (ValueExpression) expression;
        assertEquals("test", constant.value);
    }

    @Test
    public void nullValue() throws Exception {
        Expression expression = TestsUtils.parse("null");

        assertNotNull(expression);
        assertSame(ValueExpression.class, expression.getClass());

        ValueExpression constant = (ValueExpression) expression;
        assertNull(constant.value);
    }
}