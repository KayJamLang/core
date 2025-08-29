package com.github.kayjamlang.tests.statements;

import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.statements.ClassStatement;
import com.github.kayjamlang.core.statements.ObjectStatement;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ObjectStatementTest {

    @Test
    public void simpleObject() throws Exception {
        KayJamFile file = TestsUtils.getParser("object Test {const testt = 123; prop test = 123;fun test(){return test}}")
                .fillFile();
        assertEquals(1, file.classes.size());
        assertEquals(ObjectStatement.class, file.classes.getFirst().getClass());

        ObjectStatement classStatement = (ObjectStatement) file.classes.getFirst();
        assertEquals("Test", classStatement.name);
        assertEquals(0, classStatement.parentClasses.size());
        assertEquals(1, classStatement.constants.size());
        assertEquals(1, classStatement.properties.size());
        assertEquals(1, classStatement.functions.size());
    }

    @Test
    public void simpleObjectWithParents() throws Exception {
        KayJamFile file = TestsUtils.getParser("object Test: A, \\B\\A {prop test = 123;fun test(){return test}}")
                .fillFile();

        assertEquals(1, file.classes.size());
        assertEquals(ObjectStatement.class, file.classes.getFirst().getClass());

        ObjectStatement classStatement = (ObjectStatement) file.classes.getFirst();
        assertEquals("Test", classStatement.name);
        assertEquals("\\A", classStatement.parentClasses.getFirst().name);
        assertEquals("\\B\\A", classStatement.parentClasses.get(1).name);
        assertEquals(1, classStatement.properties.size());
        assertEquals(1, classStatement.functions.size());
    }
}
