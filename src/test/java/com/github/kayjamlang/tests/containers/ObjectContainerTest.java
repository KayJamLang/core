package com.github.kayjamlang.tests.containers;

import com.github.kayjamlang.core.Expression;
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
        parser = new KayJamParser(new KayJamLexer("object{var test = 123;fun test(){return test}}"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(ObjectContainer.class, expression.getClass());

        ObjectContainer objectContainer = (ObjectContainer) expression;
        assertEquals(1, objectContainer.children.size());
        assertEquals(1, objectContainer.functions.size());
    }
}