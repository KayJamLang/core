package com.github.kayjam.tests.expressions.var;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;
import com.github.kayjam.core.expressions.Const;
import com.github.kayjam.core.expressions.Variable;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class VariableTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("var test = 123"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(Variable.class, expression.getClass());

        Variable variable = (Variable) expression;
        assertEquals("test", variable.name);
        assertSame(Const.class, variable.expression.getClass());

        Const constant = (Const) variable.expression;
        assertEquals(123L, constant.value);
    }
}
