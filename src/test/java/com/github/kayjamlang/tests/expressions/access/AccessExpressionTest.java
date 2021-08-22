package com.github.kayjamlang.tests.expressions.access;

import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccessExpressionTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("getRoot().child");

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


    @Test
    public void withOperations() throws Exception {
        Expression expression = TestsUtils.parse("getRoot().child+123");

        assertNotNull(expression);
        assertSame(OperationExpression.class, expression.getClass());

        OperationExpression operationExpression = (OperationExpression) expression;
        assertSame(AccessExpression.class, operationExpression.left.getClass());
        assertSame(ValueExpression.class, operationExpression.right.getClass());

        AccessExpression accessExpression = (AccessExpression) operationExpression.left;
        assertSame(CallOrCreateExpression.class, accessExpression.root.getClass());
        assertSame(VariableLinkExpression.class, accessExpression.child.getClass());

        CallOrCreateExpression root = (CallOrCreateExpression) accessExpression.root;
        assertEquals("getRoot", root.name);
        assertEquals(0, root.arguments.size());

        VariableLinkExpression child = (VariableLinkExpression) accessExpression.child;
        assertEquals("child", child.name);
    }

    @Test
    public void big() throws Exception {
        Expression expression = TestsUtils.parse("print(a()\n" +
                ".b(c().d(\"test\")).e())");

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