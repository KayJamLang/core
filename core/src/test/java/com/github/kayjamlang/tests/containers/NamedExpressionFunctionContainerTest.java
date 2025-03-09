package com.github.kayjamlang.tests.containers;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.NamedExpressionFunctionContainer;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class NamedExpressionFunctionContainerTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("named fun test{}");

        assertNotNull(expression);
        assertSame(NamedExpressionFunctionContainer.class, expression.getClass());

        NamedExpressionFunctionContainer namedExpressionFunction = (NamedExpressionFunctionContainer) expression;
        assertEquals(0, namedExpressionFunction.children.size());
    }
}
