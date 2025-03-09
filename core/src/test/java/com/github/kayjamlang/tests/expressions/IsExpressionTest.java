package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.IsExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IsExpressionTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("123 is int");
        assertNotNull(expression);
        assertEquals(IsExpression.class, expression.getClass());

        IsExpression isExpression = (IsExpression) expression;
        assertEquals(ValueExpression.class, isExpression.expression.getClass());
        assertEquals(Type.INTEGER, isExpression.verifyType);
    }
}
