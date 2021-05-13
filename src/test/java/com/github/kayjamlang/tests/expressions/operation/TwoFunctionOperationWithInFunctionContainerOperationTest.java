package com.github.kayjamlang.tests.expressions.operation;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.data.Operation;
import com.github.kayjamlang.core.expressions.CallOrCreateExpression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.OperationExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoFunctionOperationWithInFunctionContainerOperationTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("equals(2-1)+equals(1+1)"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(OperationExpression.class, expression.getClass());

        OperationExpression operationExpression = (OperationExpression) expression;
        assertEquals(Operation.PLUS, operationExpression.operation);
        assertSame(CallOrCreateExpression.class, operationExpression.left.getClass());
        assertSame(CallOrCreateExpression.class, operationExpression.right.getClass());

        //Left
        CallOrCreateExpression callOrCreateExpressionLeft = (CallOrCreateExpression) operationExpression.left;
        assertEquals(1, callOrCreateExpressionLeft.arguments.size());
        assertSame(OperationExpression.class, callOrCreateExpressionLeft.arguments.get(0).getClass());

        OperationExpression operationExpressionLeft = (OperationExpression) callOrCreateExpressionLeft.arguments.get(0);
        assertEquals(Operation.MINUS, operationExpressionLeft.operation);
        assertSame(ValueExpression.class, operationExpressionLeft.left.getClass());
        assertSame(ValueExpression.class, operationExpressionLeft.right.getClass());

        //Right
        CallOrCreateExpression callOrCreateExpressionRight = (CallOrCreateExpression) operationExpression.right;
        assertEquals(1, callOrCreateExpressionRight.arguments.size());
        assertSame(OperationExpression.class, callOrCreateExpressionRight.arguments.get(0).getClass());

        OperationExpression operationExpressionRight = (OperationExpression) callOrCreateExpressionRight.arguments.get(0);
        assertEquals(Operation.PLUS, operationExpressionRight.operation);
        assertSame(ValueExpression.class, operationExpressionRight.left.getClass());
        assertSame(ValueExpression.class, operationExpressionRight.right.getClass());
    }
}
