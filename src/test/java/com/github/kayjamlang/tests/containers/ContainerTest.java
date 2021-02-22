package com.github.kayjamlang.tests.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContainerTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("{true;false;var test = 12345;}"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(Container.class, expression.getClass());

        Container container = (Container) expression;
        assertEquals(3, container.children.size());
    }
}