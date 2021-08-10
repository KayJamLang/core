package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.NegationExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("!true"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(NegationExpression.class, expression.getClass());

        NegationExpression notExpression = (NegationExpression) expression;
        assertSame(ValueExpression.class, notExpression.expression.getClass());

        ValueExpression constant = (ValueExpression) notExpression.expression;
        assertEquals(true, constant.value);
    }
}