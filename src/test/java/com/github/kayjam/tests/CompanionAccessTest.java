package com.github.kayjam.tests;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;
import com.github.kayjam.core.expressions.CompanionAccess;
import com.github.kayjam.core.expressions.Const;
import com.github.kayjam.core.expressions.Return;
import com.github.kayjam.core.expressions.VariableLink;
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
        assertEquals("testl", variableLink.name);
    }
}