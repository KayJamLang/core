package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.expressions.AccessExpression;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.NamedExpression;
import com.github.kayjamlang.core.expressions.VariableLinkExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class NamedExpressionTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("test{}");

        assertNotNull(expression);
        assertSame(NamedExpression.class, expression.getClass());

        NamedExpression namedContainer = (NamedExpression) expression;
        assertSame(Container.class, namedContainer.expression.getClass());
    }

    @Test
    public void inAccessExpression() throws Exception {
        Expression expression = TestsUtils.parse("test.test {}");

        assertNotNull(expression);
        assertSame(AccessExpression.class, expression.getClass());

        AccessExpression accessExpression = (AccessExpression) expression;
        assertSame(VariableLinkExpression.class, accessExpression.root.getClass());
        assertSame(NamedExpression.class, accessExpression.child.getClass());

        NamedExpression namedContainer = (NamedExpression) accessExpression.child;
        assertSame(Container.class, namedContainer.expression.getClass());
    }
}