package com.github.kayjamlang.tests.containers.classes;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.ObjectContainer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectContainerTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("object Test{var test = 123;fun test(){return test}}"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readTopExpression();

        assertNotNull(expression);
        assertSame(ObjectContainer.class, expression.getClass());

        ObjectContainer objectContainer = (ObjectContainer) expression;
        assertEquals("Test", objectContainer.name);
        assertEquals(0, objectContainer.variables.size());
        assertEquals(0, objectContainer.functions.size());

        objectContainer = objectContainer.companion;
        assertNotNull(objectContainer);
        assertEquals(1, objectContainer.variables.size());
        assertEquals(1, objectContainer.functions.size());
    }
}