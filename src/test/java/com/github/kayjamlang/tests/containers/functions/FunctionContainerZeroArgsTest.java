package com.github.kayjamlang.tests.containers.functions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.FunctionContainer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionContainerZeroArgsTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("fun test(){true;false}"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(FunctionContainer.class, expression.getClass());

        FunctionContainer functionContainer = (FunctionContainer) expression;
        assertEquals(0, functionContainer.arguments.size());
        assertEquals(2, functionContainer.children.size());
    }
}