package com.github.kayjamlang.tests.expressions.var;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.VariableSetExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class VariableSetExpressionTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("test = 123");

        assertNotNull(expression);
        assertSame(VariableSetExpression.class, expression.getClass());

        VariableSetExpression variable = (VariableSetExpression) expression;
        assertEquals("test", variable.name);
        assertSame(ValueExpression.class, variable.expression.getClass());

        ValueExpression constant = (ValueExpression) variable.expression;
        assertEquals(123, constant.value);
    }
}
