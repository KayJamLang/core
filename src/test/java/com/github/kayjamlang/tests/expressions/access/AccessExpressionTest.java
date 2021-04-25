package com.github.kayjamlang.tests.expressions.access;

import com.github.kayjamlang.core.expressions.AccessExpression;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.CallOrCreateExpression;
import com.github.kayjamlang.core.expressions.VariableLinkExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccessExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("getRoot().child"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(AccessExpression.class, expression.getClass());

        AccessExpression accessExpression = (AccessExpression) expression;
        assertSame(CallOrCreateExpression.class, accessExpression.root.getClass());
        assertSame(VariableLinkExpression.class, accessExpression.child.getClass());

        CallOrCreateExpression root = (CallOrCreateExpression) accessExpression.root;
        assertEquals("getRoot", root.name);
        assertEquals(0, root.arguments.size());

        VariableLinkExpression child = (VariableLinkExpression) accessExpression.child;
        assertEquals("child", child.name);
    }
}