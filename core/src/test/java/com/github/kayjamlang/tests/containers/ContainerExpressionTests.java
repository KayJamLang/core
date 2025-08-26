package com.github.kayjamlang.tests.containers;

import com.github.kayjamlang.core.containers.ContainerExpression;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContainerExpressionTests {

    @Test
    public void empty() throws Exception {
        Expression expression = TestsUtils.parseExpression("{}");

        assertNotNull(expression);
        assertSame(ContainerExpression.class, expression.getClass());

        ContainerExpression containerExpression = (ContainerExpression) expression;
        assertEquals(0, containerExpression.children.size());
    }

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parseExpression("{true;false;var test = 12345;}");

        assertNotNull(expression);
        assertSame(ContainerExpression.class, expression.getClass());

        ContainerExpression containerExpression = (ContainerExpression) expression;
        assertEquals(3, containerExpression.children.size());
    }
}