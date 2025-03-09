package com.github.kayjamlang.tests.containers;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContainerTests {

    @Test
    public void empty() throws Exception {
        Expression expression = TestsUtils.parse("{}");

        assertNotNull(expression);
        assertSame(Container.class, expression.getClass());

        Container container = (Container) expression;
        assertEquals(0, container.children.size());
    }

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("{true;false;var test = 12345;}");

        assertNotNull(expression);
        assertSame(Container.class, expression.getClass());

        Container container = (Container) expression;
        assertEquals(3, container.children.size());
    }
}