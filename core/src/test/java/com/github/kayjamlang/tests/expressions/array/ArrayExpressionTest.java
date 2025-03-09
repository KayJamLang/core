package com.github.kayjamlang.tests.expressions.array;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ArrayExpression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;

public class ArrayExpressionTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("[1,2,3,4,5]");

        assertNotNull(expression);
        assertSame(ArrayExpression.class, expression.getClass());

        ArrayExpression arrayExpression = (ArrayExpression) expression;
        assertEquals(5, arrayExpression.values.size());

        for (int i = 0; i < arrayExpression.values.size(); i++) {
            Expression value = arrayExpression.values.get(i);
            assertSame(ValueExpression.class, value.getClass());

            ValueExpression constantValue = (ValueExpression) value;
            assertEquals(i+1, constantValue.value);
        }
    }
}