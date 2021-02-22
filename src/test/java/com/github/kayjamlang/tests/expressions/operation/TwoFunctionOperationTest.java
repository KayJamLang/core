package com.github.kayjamlang.tests.expressions.operation;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.Operation;
import com.github.kayjamlang.core.expressions.CallCreate;
import com.github.kayjamlang.core.expressions.OperationExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoFunctionOperationTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("equals(2)+equals(1)"));
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
    }
}
