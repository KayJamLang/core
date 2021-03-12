package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.NamedExpressionFunction;
import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.expressions.Not;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class NamedExpressionFunctionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("named fun test{}"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(NamedExpressionFunction.class, expression.getClass());

        NamedExpressionFunction namedExpressionFunction = (NamedExpressionFunction) expression;
        assertEquals(0, namedExpressionFunction.children.size());
    }
}
