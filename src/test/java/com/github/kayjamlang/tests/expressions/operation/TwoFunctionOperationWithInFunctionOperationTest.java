package com.github.kayjamlang.tests.expressions.operation;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.Operation;
import com.github.kayjamlang.core.expressions.CallCreate;
import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.expressions.OperationExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoFunctionOperationWithInFunctionOperationTest {

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
        assertSame(CallCreate.class, operationExpression.left.getClass());
        assertSame(CallCreate.class, operationExpression.right.getClass());

        //Left
        CallCreate callCreateLeft = (CallCreate) operationExpression.left;
        assertEquals(1, callCreateLeft.arguments.size());
        assertSame(OperationExpression.class, callCreateLeft.arguments.get(0).getClass());

        OperationExpression operationExpressionLeft = (OperationExpression) callCreateLeft.arguments.get(0);
        assertEquals(Operation.MINUS, operationExpressionLeft.operation);
        assertSame(Const.class, operationExpressionLeft.left.getClass());
        assertSame(Const.class, operationExpressionLeft.right.getClass());

        //Right
        CallCreate callCreateRight = (CallCreate) operationExpression.right;
        assertEquals(1, callCreateRight.arguments.size());
        assertSame(OperationExpression.class, callCreateRight.arguments.get(0).getClass());

        OperationExpression operationExpressionRight = (OperationExpression) callCreateRight.arguments.get(0);
        assertEquals(Operation.PLUS, operationExpressionRight.operation);
        assertSame(Const.class, operationExpressionRight.left.getClass());
        assertSame(Const.class, operationExpressionRight.right.getClass());
    }
}
