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

public class InFunctionOperationTest{

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("equals(2+2)"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(CallCreate.class, expression.getClass());

        CallCreate callCreate = (CallCreate) expression;
        assertEquals(1, callCreate.arguments.size());
        assertSame(OperationExpression.class, callCreate.arguments.get(0).getClass());

        OperationExpression operationExpression = (OperationExpression) callCreate.arguments.get(0);
        assertEquals(Operation.PLUS, operationExpression.operation);
        assertSame(Const.class, operationExpression.left.getClass());
        assertSame(Const.class, operationExpression.right.getClass());
    }
}
