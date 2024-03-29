package com.github.kayjamlang.tests.expressions.operation;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.data.Operation;
import com.github.kayjamlang.core.expressions.CallOrCreateExpression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.OperationExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class InFunctionContainerOperationTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("equals(2+2)");

        assertNotNull(expression);
        assertSame(CallOrCreateExpression.class, expression.getClass());

        CallOrCreateExpression callOrCreateExpression = (CallOrCreateExpression) expression;
        assertEquals(1, callOrCreateExpression.arguments.size());
        assertSame(OperationExpression.class, callOrCreateExpression.arguments.get(0).getClass());

        OperationExpression operationExpression = (OperationExpression) callOrCreateExpression.arguments.get(0);
        assertEquals(Operation.PLUS, operationExpression.operation);
        assertSame(ValueExpression.class, operationExpression.left.getClass());
        assertSame(ValueExpression.class, operationExpression.right.getClass());
    }
}
