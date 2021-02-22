package com.github.kayjamlang.tests.expressions.access;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.Access;
import com.github.kayjamlang.core.expressions.VariableLink;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccessTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("root.child"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(Access.class, expression.getClass());

        Access access = (Access) expression;
        assertSame(VariableLink.class, access.root.getClass());
        assertSame(VariableLink.class, access.child.getClass());

        VariableLink root = (VariableLink) access.root;
        assertEquals("root", root.name);

        VariableLink child = (VariableLink) access.child;
        assertEquals("child", child.name);
    }
}