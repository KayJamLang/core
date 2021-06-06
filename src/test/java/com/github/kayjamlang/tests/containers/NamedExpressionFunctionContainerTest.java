package com.github.kayjamlang.tests.containers;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.NamedExpressionFunctionContainer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class NamedExpressionFunctionContainerTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("named fun test{}"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(NamedExpressionFunctionContainer.class, expression.getClass());

        NamedExpressionFunctionContainer namedExpressionFunction = (NamedExpressionFunctionContainer) expression;
        assertEquals(0, namedExpressionFunction.children.size());
    }
}
