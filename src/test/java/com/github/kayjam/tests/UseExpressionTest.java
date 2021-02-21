package com.github.kayjam.tests;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;
import com.github.kayjam.core.expressions.Const;
import com.github.kayjam.core.expressions.If;
import com.github.kayjam.core.expressions.Use;
import com.github.kayjam.core.expressions.VariableLink;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UseExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("use 12345"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(Use.class, expression.getClass());

        Use useExpression = (Use) expression;
        assertSame(Const.class, useExpression.expression.getClass());

        Const ifTrue = (Const) useExpression.expression;
        assertEquals(12345, ifTrue.value);
    }
}