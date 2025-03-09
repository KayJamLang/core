package com.github.kayjamlang.tests.expressions.array;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.GetExpression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.VariableLinkExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayExpressionGetTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("test[2005]");

        assertNotNull(expression);
        assertSame(GetExpression.class, expression.getClass());

        GetExpression arrayGet = (GetExpression) expression;
        assertSame(VariableLinkExpression.class, arrayGet.root.getClass());
        assertSame(ValueExpression.class, arrayGet.value.getClass());

        VariableLinkExpression variableLinkExpression = (VariableLinkExpression) arrayGet.root;
        assertEquals("test", variableLinkExpression.name);

        ValueExpression constant = (ValueExpression) arrayGet.value;
        assertEquals(2005, constant.value);
    }

    @Test
    public void more() throws Exception {
        Expression expression = TestsUtils.parse("test[2005][2006]");

        assertNotNull(expression);
        assertSame(GetExpression.class, expression.getClass());

        GetExpression arrayGet = (GetExpression) expression;
        assertSame(GetExpression.class, arrayGet.root.getClass());
        assertSame(ValueExpression.class, arrayGet.value.getClass());

        GetExpression getExpression = (GetExpression) arrayGet.root;
        assertSame(VariableLinkExpression.class, getExpression.root.getClass());
        assertSame(ValueExpression.class, getExpression.value.getClass());

        ValueExpression constant = (ValueExpression) arrayGet.value;
        assertEquals(2006, constant.value);
    }
}