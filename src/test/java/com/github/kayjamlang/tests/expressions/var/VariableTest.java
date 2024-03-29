package com.github.kayjamlang.tests.expressions.var;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.VariableExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class VariableTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("var test = 123");

        assertNotNull(expression);
        assertSame(VariableExpression.class, expression.getClass());

        VariableExpression variable = (VariableExpression) expression;
        assertEquals("test", variable.name);
        assertSame(ValueExpression.class, variable.expression.getClass());

        ValueExpression constant = (ValueExpression) variable.expression;
        assertEquals(123, constant.value);
    }
}
