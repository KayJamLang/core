package com.github.kayjamlang.tests.containers.functions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.FunctionRefExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionContainerRefExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("-> (test) true"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(FunctionRefExpression.class, expression.getClass());

        FunctionRefExpression functionRefExpression = (FunctionRefExpression) expression;
        assertEquals(1, functionRefExpression.arguments.size());
        assertEquals("test", functionRefExpression.arguments.get(0).name);
        assertEquals(Type.ANY, functionRefExpression.arguments.get(0).type);
        assertSame(ValueExpression.class, functionRefExpression.expression.getClass());

        ValueExpression constant = (ValueExpression) functionRefExpression.expression;
        assertEquals(true, constant.value);
    }
}