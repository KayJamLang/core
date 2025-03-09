package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.ReturnExpression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReturnExpressionTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("return 2005");

        assertNotNull(expression);
        assertSame(ReturnExpression.class, expression.getClass());

        ReturnExpression returnExpression = (ReturnExpression) expression;
        assertSame(ValueExpression.class, returnExpression.expression.getClass());

        ValueExpression constant = (ValueExpression) returnExpression.expression;
        assertEquals(2005, constant.value);
    }
}