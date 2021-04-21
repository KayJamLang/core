package com.github.kayjamlang.tests.containers.functions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.FunctionRefExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionContainerRefZeroArgsExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("-> true"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(FunctionRefExpression.class, expression.getClass());

        FunctionRefExpression functionRefExpression = (FunctionRefExpression) expression;
        assertEquals(0, functionRefExpression.arguments.size());
        assertSame(ValueExpression.class, functionRefExpression.expression.getClass());

        ValueExpression constant = (ValueExpression) functionRefExpression.expression;
        assertEquals(true, constant.value);
    }
}