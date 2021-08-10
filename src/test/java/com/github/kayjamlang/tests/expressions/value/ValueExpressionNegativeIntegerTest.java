package com.github.kayjamlang.tests.expressions.value;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.OperationExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValueExpressionNegativeIntegerTest {

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
        assertSame(ValueExpression.class, operationExpression.left.getClass());
        assertSame(ValueExpression.class, operationExpression.right.getClass());

        ValueExpression constant = (ValueExpression) operationExpression.right;
        assertEquals(12345, constant.value);

        constant = (ValueExpression) operationExpression.left;
        assertEquals(-1, constant.value);
    }
}