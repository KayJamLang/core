package com.github.kayjam.tests;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;
import com.github.kayjam.core.containers.ClassContainer;
import com.github.kayjam.core.containers.ObjectContainer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassContainerTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("class Test {var test = 123;fun test(){return test}}"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(ClassContainer.class, expression.getClass());

        ClassContainer classContainer = (ClassContainer) expression;
        assertEquals("Test", classContainer.name);
        assertNull(classContainer.extendsClass);
        assertEquals(0, classContainer.implementsClass.size());
        assertEquals(1, classContainer.children.size());
        assertEquals(1, classContainer.functions.size());
    }
}