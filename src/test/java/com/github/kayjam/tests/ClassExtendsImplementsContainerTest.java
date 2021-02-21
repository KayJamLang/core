package com.github.kayjam.tests;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;
import com.github.kayjam.core.containers.ClassContainer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassExtendsImplementsContainerTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("class Test: ABC:: AAA {var test = 123;fun test(){return test}}"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(ClassContainer.class, expression.getClass());

        ClassContainer classContainer = (ClassContainer) expression;
        assertEquals("Test", classContainer.name);
        assertEquals("ABC", classContainer.extendsClass);
        assertEquals(1, classContainer.implementsClass.size());
        assertEquals("AAA", classContainer.implementsClass.get(0));
        assertEquals(1, classContainer.children.size());
        assertEquals(1, classContainer.functions.size());
    }
}