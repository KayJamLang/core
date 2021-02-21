package com.github.kayjam.tests;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;
import com.github.kayjam.core.Type;
import com.github.kayjam.core.containers.Function;
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

        Function.Argument argument = function.arguments.get(0);
        assertEquals(Type.STRING, argument.type);
        assertEquals("value", argument.name);
    }
}