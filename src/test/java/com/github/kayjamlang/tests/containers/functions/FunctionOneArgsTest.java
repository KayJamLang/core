package com.github.kayjamlang.tests.containers.functions;

import com.github.kayjamlang.core.*;
import com.github.kayjamlang.core.containers.Function;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionOneArgsTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("fun test(string value){true;false}"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(Function.class, expression.getClass());

        Function function = (Function) expression;
        assertEquals(1, function.arguments.size());
        assertEquals(2, function.children.size());

        Argument argument = function.arguments.get(0);
        assertEquals(Type.STRING, argument.type);
        assertEquals("value", argument.name);
    }
}