package com.github.kayjam.tests.expressions;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;
import com.github.kayjam.core.expressions.ArrayGet;
import com.github.kayjam.core.expressions.Const;
import com.github.kayjam.core.expressions.Not;
import com.github.kayjam.core.expressions.VariableLink;
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
        assertSame(Not.class, expression.getClass());

        Not notExpression = (Not) expression;
        assertSame(Const.class, notExpression.expression.getClass());

        Const constant = (Const) notExpression.expression;
        assertEquals(true, constant.value);
    }
}