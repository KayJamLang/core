package com.github.kayjam.tests;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;
import com.github.kayjam.core.expressions.Const;
import com.github.kayjam.core.expressions.FunctionRef;
import com.github.kayjam.core.expressions.If;
import com.github.kayjam.core.expressions.VariableLink;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionRefExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("-> (test) true"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(FunctionRef.class, expression.getClass());

        FunctionRef functionRefExpression = (FunctionRef) expression;
        assertEquals(1, functionRefExpression.arguments.size());
        assertEquals("test", functionRefExpression.arguments.get(0));
        assertSame(Const.class, functionRefExpression.expression.getClass());

        Const constant = (Const) functionRefExpression.expression;
        assertEquals(true, constant.value);
    }
}