package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.CastExpression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CastExpressionTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("123 as int");
        assertNotNull(expression);
        assertEquals(CastExpression.class, expression.getClass());

        CastExpression castExpression = (CastExpression) expression;
        assertEquals(ValueExpression.class, castExpression.expression.getClass());
        assertEquals(Type.INTEGER, castExpression.castType);
    }
}
