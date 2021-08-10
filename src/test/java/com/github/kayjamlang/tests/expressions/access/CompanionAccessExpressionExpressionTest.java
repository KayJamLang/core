package com.github.kayjamlang.tests.expressions.access;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.CompanionAccessExpression;
import com.github.kayjamlang.core.expressions.VariableLinkExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CompanionAccessExpressionExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("Test::test"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(CompanionAccessExpression.class, expression.getClass());

        CompanionAccessExpression companionAccessExpression = (CompanionAccessExpression) expression;
        assertEquals("Test", companionAccessExpression.className);
        assertSame(VariableLinkExpression.class, companionAccessExpression.child.getClass());

        VariableLinkExpression variableLinkExpression = (VariableLinkExpression) companionAccessExpression.child;
        assertEquals("test", variableLinkExpression.name);
    }
}