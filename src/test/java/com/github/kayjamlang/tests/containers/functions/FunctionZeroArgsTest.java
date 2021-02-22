package com.github.kayjamlang.tests.containers.functions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Function;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionZeroArgsTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("fun test(){true;false}"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(Function.class, expression.getClass());

        Function function = (Function) expression;
        assertEquals(0, function.arguments.size());
        assertEquals(2, function.children.size());
    }
}