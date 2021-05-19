package com.github.kayjamlang.tests.expressions.value;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ValueExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValueExpressionNullTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("null"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(ValueExpression.class, expression.getClass());

        ValueExpression constant = (ValueExpression) expression;
        assertNull(constant.value);
    }
}