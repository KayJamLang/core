package com.github.kayjamlang.tests.expressions.var;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.*;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class VariableWithAccessTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("var test = getRequest().query[\"test\"]"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(Variable.class, expression.getClass());

        Variable variable = (Variable) expression;
        assertEquals("test", variable.name);
        assertSame(GetExpression.class, variable.expression.getClass());

        GetExpression getExpression = (GetExpression) variable.expression;
        assertSame(Access.class, getExpression.root.getClass());
    }
}
