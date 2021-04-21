package com.github.kayjamlang.tests.expressions.var;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.VariableLinkExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class VariableLinkTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("test"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(VariableLinkExpression.class, expression.getClass());

        VariableLinkExpression variableLinkExpression = (VariableLinkExpression) expression;
        assertEquals("test", variableLinkExpression.name);
    }
}
