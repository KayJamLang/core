package com.github.kayjamlang.tests.expressions.access;

import com.github.kayjamlang.core.expressions.AccessExpression;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.CallOrCreateExpression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class BigAccessExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("print(a()\n" +
                ".b(c().d(\"test\")).e())"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(CallOrCreateExpression.class, expression.getClass());

        CallOrCreateExpression callOrCreateExpression = (CallOrCreateExpression) expression;
        assertEquals("print", callOrCreateExpression.name);
        assertEquals(1, callOrCreateExpression.arguments.size());
        assertSame(AccessExpression.class, callOrCreateExpression.arguments.get(0).getClass());

        AccessExpression accessExpression = (AccessExpression) callOrCreateExpression.arguments.get(0);
        assertSame(AccessExpression.class, accessExpression.root.getClass());
        assertSame(CallOrCreateExpression.class, accessExpression.child.getClass());

        callOrCreateExpression = (CallOrCreateExpression) accessExpression.child;
        assertEquals("e", callOrCreateExpression.name);
        assertEquals(0, callOrCreateExpression.arguments.size());

        accessExpression = (AccessExpression) accessExpression.root;
        assertSame(CallOrCreateExpression.class, accessExpression.root.getClass());
        assertSame(CallOrCreateExpression.class, accessExpression.child.getClass());

        callOrCreateExpression = (CallOrCreateExpression) accessExpression.root;
        assertEquals("a", callOrCreateExpression.name);
        assertEquals(0, callOrCreateExpression.arguments.size());

        callOrCreateExpression = (CallOrCreateExpression) accessExpression.child;
        assertEquals("b", callOrCreateExpression.name);
        assertEquals(1, callOrCreateExpression.arguments.size());
        assertSame(AccessExpression.class, callOrCreateExpression.arguments.get(0).getClass());

        accessExpression = (AccessExpression) callOrCreateExpression.arguments.get(0);
        assertSame(CallOrCreateExpression.class, accessExpression.root.getClass());
        assertSame(CallOrCreateExpression.class, accessExpression.child.getClass());

        callOrCreateExpression = (CallOrCreateExpression) accessExpression.root;
        assertEquals("c", callOrCreateExpression.name);
        assertEquals(0, callOrCreateExpression.arguments.size());

        callOrCreateExpression = (CallOrCreateExpression) accessExpression.child;
        assertEquals("d", callOrCreateExpression.name);
        assertEquals(1, callOrCreateExpression.arguments.size());
        assertSame(ValueExpression.class, callOrCreateExpression.arguments.get(0).getClass());
    }
}