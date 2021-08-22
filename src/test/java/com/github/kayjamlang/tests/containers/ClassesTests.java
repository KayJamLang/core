package com.github.kayjamlang.tests.containers;

import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ClassesTests {

    @Test
    public void anonymousObject() throws Exception {
        Expression expression = TestsUtils.getParser("object{var test = 123;fun test(){return test}}")
                .readTopExpression();

        assertNotNull(expression);
        assertSame(ObjectContainer.class, expression.getClass());

        ObjectContainer objectContainer = (ObjectContainer) expression;
        assertEquals(1, objectContainer.variables.size());
        assertEquals(1, objectContainer.functions.size());
    }

    @Test
    public void class_() throws Exception {
        Expression expression = TestsUtils.getParser("class Test {var test = 123;fun test(){return test}}")
                .readTopExpression();

        assertNotNull(expression);
        assertSame(ClassContainer.class, expression.getClass());

        ClassContainer classContainer = (ClassContainer) expression;
        assertEquals("Test", classContainer.name);
        assertNull(classContainer.extendsClass);
        assertEquals(0, classContainer.implementsClass.size());
        assertEquals(1, classContainer.variables.size());
        assertEquals(1, classContainer.functions.size());
    }

    @Test
    public void classExtends() throws Exception {
        Expression expression = TestsUtils.getParser("class Test: ABC {var test = 123;fun test(){return test}}")
                .readTopExpression();

        assertNotNull(expression);
        assertSame(ClassContainer.class, expression.getClass());

        ClassContainer classContainer = (ClassContainer) expression;
        assertEquals("Test", classContainer.name);
        assertEquals("ABC", classContainer.extendsClass);
        assertEquals(0, classContainer.implementsClass.size());
        assertEquals(1, classContainer.variables.size());
        assertEquals(1, classContainer.functions.size());
    }

    @Test
    public void classImplements() throws Exception {
        Expression expression = TestsUtils.getParser("class Test:: ABC {var test = 123;fun test(){return test}}")
                .readTopExpression();

        assertNotNull(expression);
        assertSame(ClassContainer.class, expression.getClass());

        ClassContainer classContainer = (ClassContainer) expression;
        assertEquals("Test", classContainer.name);
        assertEquals(1, classContainer.implementsClass.size());
        assertEquals("ABC", classContainer.implementsClass.get(0));
        assertEquals(1, classContainer.variables.size());
        assertEquals(1, classContainer.functions.size());
    }

    @Test
    public void object() throws Exception {
        Expression expression = TestsUtils.getParser("object Test{var test = 123;fun test(){return test}}")
                .readTopExpression();

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
