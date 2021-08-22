package com.github.kayjamlang.tests.expressions.operation;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.data.Operation;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.OperationExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShortOperationTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("2++");

        assertNotNull(expression);
        assertSame(OperationExpression.class, expression.getClass());

        OperationExpression operationExpression = (OperationExpression) expression;
        assertEquals(Operation.PLUS, operationExpression.operation);
        assertSame(ValueExpression.class, operationExpression.left.getClass());
        assertSame(ValueExpression.class, operationExpression.right.getClass());

        ValueExpression right = (ValueExpression) operationExpression.right;
        assertEquals(right.value, 1);
    }
}
