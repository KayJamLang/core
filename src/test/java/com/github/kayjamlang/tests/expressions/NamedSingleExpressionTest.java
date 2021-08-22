package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.NamedExpression;
import com.github.kayjamlang.core.expressions.CallOrCreateExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class NamedSingleExpressionTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("test -> println()");

        assertNotNull(expression);
        assertSame(NamedExpression.class, expression.getClass());

        NamedExpression namedContainer = (NamedExpression) expression;
        assertSame(CallOrCreateExpression.class, namedContainer.expression.getClass());
    }
}