package com.github.kayjamlang.tests.expressions.var;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.VariableLinkExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class VariableLinkTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("test");

        assertNotNull(expression);
        assertSame(VariableLinkExpression.class, expression.getClass());

        VariableLinkExpression variableLinkExpression = (VariableLinkExpression) expression;
        assertEquals("test", variableLinkExpression.name);
    }
}
