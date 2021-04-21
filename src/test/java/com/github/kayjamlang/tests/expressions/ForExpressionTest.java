package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.loops.ForExpression;
import com.github.kayjamlang.core.expressions.OperationExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ForExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("for(test in 0..2) true"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(ForExpression.class, expression.getClass());

        ForExpression forExpression = (ForExpression) expression;
        assertEquals("test", forExpression.variableName);
        assertSame(OperationExpression.class, forExpression.range.getClass());
        assertSame(ValueExpression.class, forExpression.body.getClass());

        ValueExpression condition = (ValueExpression) forExpression.body;
        assertEquals(true, condition.value);
    }
}