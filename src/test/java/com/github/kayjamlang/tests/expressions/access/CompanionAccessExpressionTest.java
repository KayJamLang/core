package com.github.kayjamlang.tests.expressions.access;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.CompanionAccessExpression;
import com.github.kayjamlang.core.expressions.VariableLinkExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CompanionAccessExpressionTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("Test::test");

        assertNotNull(expression);
        assertSame(CompanionAccessExpression.class, expression.getClass());

        CompanionAccessExpression companionAccessExpression = (CompanionAccessExpression) expression;
        assertEquals("Test", companionAccessExpression.className);
        assertSame(VariableLinkExpression.class, companionAccessExpression.child.getClass());

        VariableLinkExpression variableLinkExpression = (VariableLinkExpression) companionAccessExpression.child;
        assertEquals("test", variableLinkExpression.name);
    }
}