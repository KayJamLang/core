package com.github.kayjam.tests;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;
import com.github.kayjam.core.Type;
import com.github.kayjam.core.containers.Container;
import com.github.kayjam.core.containers.Function;
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