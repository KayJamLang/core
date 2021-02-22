package com.github.kayjam.tests.expressions.constExpr;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;
import com.github.kayjam.core.expressions.Const;
import com.github.kayjam.core.expressions.OperationExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConstNegativeIntegerTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("-12345"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(OperationExpression.class, expression.getClass());

        OperationExpression operationExpression = (OperationExpression) expression;
        assertSame(Const.class, operationExpression.left.getClass());
        assertSame(Const.class, operationExpression.right.getClass());

        Const constant = (Const) operationExpression.right;
        assertEquals(12345L, constant.value);

        constant = (Const) operationExpression.left;
        assertEquals(-1, constant.value);
    }
}