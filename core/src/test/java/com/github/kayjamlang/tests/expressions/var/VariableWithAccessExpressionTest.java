package com.github.kayjamlang.tests.expressions.var;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class VariableWithAccessExpressionTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parseExpression("var test = getRequest().query[\"test\"]");

        assertNotNull(expression);
        assertSame(VariableExpression.class, expression.getClass());

        VariableExpression variable = (VariableExpression) expression;
        assertEquals("test", variable.name);
        assertSame(GetExpression.class, variable.expression.getClass());

        GetExpression getExpression = (GetExpression) variable.expression;
        assertSame(AccessExpression.class, getExpression.root.getClass());
    }
}
