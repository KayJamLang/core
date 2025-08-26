package com.github.kayjamlang.tests.containers;

import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.statements.ClassStatement;
import com.github.kayjamlang.core.statements.ObjectStatement;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ClassesTests {

    @Test
    public void class_() throws Exception {
        KayJamFile file = TestsUtils.getParser("class Test {const testt = 123; prop test = 123;fun test(){return test}}")
                        .fillFile();
        assertEquals(1, file.classes.size());

        ClassStatement classStatement = file.classes.getFirst();
        assertEquals("Test", classStatement.name);
        assertNull(classStatement.extendsClass);
        assertEquals(0, classStatement.implementsClass.size());
        assertEquals(1, classStatement.constants.size());
        assertEquals(1, classStatement.properties.size());
        assertEquals(1, classStatement.functions.size());
    }

    @Test
    public void classExtends() throws Exception {
        KayJamFile file = TestsUtils.getParser("class Test: ABC {prop test = 123;fun test(){return test}}")
                .fillFile();

        assertEquals(1, file.classes.size());

        ClassStatement classStatement = file.classes.getFirst();
        assertEquals("Test", classStatement.name);
        assertEquals("ABC", classStatement.extendsClass);
        assertEquals(0, classStatement.implementsClass.size());
        assertEquals(1, classStatement.properties.size());
        assertEquals(1, classStatement.functions.size());
    }

    @Test
    public void classImplements() throws Exception {
        KayJamFile file = TestsUtils.getParser("class Test:: ABC {prop test = 123;fun test(){return test}}")
                .fillFile();

        assertEquals(1, file.classes.size());

        ClassStatement classStatement = file.classes.getFirst();
        assertEquals("Test", classStatement.name);
        assertEquals(1, classStatement.implementsClass.size());
        assertEquals("ABC", classStatement.implementsClass.getFirst());
        assertEquals(1, classStatement.properties.size());
        assertEquals(1, classStatement.functions.size());
    }

    @Test
    public void object() throws Exception {
        KayJamFile file = TestsUtils.getParser("object Test{prop test = 123;fun test(){return test}}")
                .fillFile();

        assertEquals(1, file.classes.size());

        ObjectStatement objectContainer = (ObjectStatement) file.classes.getFirst();
        assertEquals("Test", objectContainer.name);
        assertEquals(1, objectContainer.properties.size());
        assertEquals(1, objectContainer.functions.size());
    }
}
