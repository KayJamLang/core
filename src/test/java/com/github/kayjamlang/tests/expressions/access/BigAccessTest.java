package com.github.kayjamlang.tests.expressions.access;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.Access;
import com.github.kayjamlang.core.expressions.CallCreate;
import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.expressions.VariableLink;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class BigAccessTest {

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
        assertSame(CallCreate.class, expression.getClass());

        CallCreate callCreate = (CallCreate) expression;
        assertEquals("print", callCreate.functionName);
        assertEquals(1, callCreate.arguments.size());
        assertSame(Access.class, callCreate.arguments.get(0).getClass());

        Access access = (Access) callCreate.arguments.get(0);
        assertSame(Access.class, access.root.getClass());
        assertSame(CallCreate.class, access.child.getClass());

        callCreate = (CallCreate) access.child;
        assertEquals("e", callCreate.functionName);
        assertEquals(0, callCreate.arguments.size());

        access = (Access) access.root;
        assertSame(CallCreate.class, access.root.getClass());
        assertSame(CallCreate.class, access.child.getClass());

        callCreate = (CallCreate) access.root;
        assertEquals("a", callCreate.functionName);
        assertEquals(0, callCreate.arguments.size());

        callCreate = (CallCreate) access.child;
        assertEquals("b", callCreate.functionName);
        assertEquals(1, callCreate.arguments.size());
        assertSame(Access.class, callCreate.arguments.get(0).getClass());

        access = (Access) callCreate.arguments.get(0);
        assertSame(CallCreate.class, access.root.getClass());
        assertSame(CallCreate.class, access.child.getClass());

        callCreate = (CallCreate) access.root;
        assertEquals("c", callCreate.functionName);
        assertEquals(0, callCreate.arguments.size());

        callCreate = (CallCreate) access.child;
        assertEquals("d", callCreate.functionName);
        assertEquals(1, callCreate.arguments.size());
        assertSame(Const.class, callCreate.arguments.get(0).getClass());
    }
}