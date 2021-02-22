package com.github.kayjamlang.tests.expressions.access;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.CompanionAccess;
import com.github.kayjamlang.core.expressions.VariableLink;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CompanionAccessTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("Test::test"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(CompanionAccess.class, expression.getClass());

        CompanionAccess companionAccess = (CompanionAccess) expression;
        assertEquals("Test", companionAccess.className);
        assertSame(VariableLink.class, companionAccess.child.getClass());

        VariableLink variableLink = (VariableLink) companionAccess.child;
        assertEquals("test", variableLink.name);
    }
}