package com.github.kayjamlang.tests.expressions.var;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.expressions.VariableSet;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class VariableSetTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("test = 123"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(VariableSet.class, expression.getClass());

        VariableSet variable = (VariableSet) expression;
        assertEquals("test", variable.name);
        assertSame(Const.class, variable.expression.getClass());

        Const constant = (Const) variable.expression;
        assertEquals(123L, constant.value);
    }
}
