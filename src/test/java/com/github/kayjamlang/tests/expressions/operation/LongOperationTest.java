package com.github.kayjamlang.tests.expressions.operation;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.OperationExpression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.data.Operation;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertSame;

public class LongOperationTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("2 + 4 * 8");

        assertNotNull(expression);
        assertSame(OperationExpression.class, expression.getClass());

        OperationExpression operationExpression = (OperationExpression) expression;
        assertEquals(Operation.PLUS, operationExpression.operation);
        assertSame(ValueExpression.class, operationExpression.left.getClass());
        assertSame(OperationExpression.class, operationExpression.right.getClass());

        OperationExpression rightOperationExpression = (OperationExpression) operationExpression.right;
        assertEquals(Operation.MULTIPLY, rightOperationExpression.operation);
        assertSame(ValueExpression.class, rightOperationExpression.right.getClass());
    }
}
