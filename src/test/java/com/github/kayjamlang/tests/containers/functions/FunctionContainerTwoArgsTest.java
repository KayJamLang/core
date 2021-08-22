package com.github.kayjamlang.tests.containers.functions;

import com.github.kayjamlang.core.*;
import com.github.kayjamlang.core.containers.FunctionContainer;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.data.Argument;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionContainerTwoArgsTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("fun test(string value, int integer){true;false}"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(FunctionContainer.class, expression.getClass());

        FunctionContainer functionContainer = (FunctionContainer) expression;
        assertEquals(2, functionContainer.arguments.size());
        assertEquals(2, functionContainer.children.size());

        Argument argument = functionContainer.arguments.get(0);
        assertEquals(Type.STRING, argument.type);
        assertEquals("value", argument.name);

        argument = functionContainer.arguments.get(1);
        assertEquals(Type.INTEGER, argument.type);
        assertEquals("integer", argument.name);
    }
}