package com.github.kayjamlang.tests.statements;

import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.statements.ClassStatement;
import com.github.kayjamlang.core.statements.ObjectStatement;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClassStatementTest {

    @Test
    public void simpleClass() throws Exception {
        KayJamFile file = TestsUtils.getParser("class Test {const testt = 123; prop test = 123;fun test(){return test}}")
                        .fillFile();
        assertEquals(1, file.classes.size());

        ClassStatement classStatement = file.classes.getFirst();
        assertEquals("Test", classStatement.name);
        assertEquals(0, classStatement.parentClasses.size());
        assertEquals(1, classStatement.constants.size());
        assertEquals(1, classStatement.properties.size());
        assertEquals(1, classStatement.functions.size());
    }

    @Test
    public void simpleClassWithParents() throws Exception {
        KayJamFile file = TestsUtils.getParser("class Test: A, \\B\\A {prop test = 123;fun test(){return test}}")
                .fillFile();

        assertEquals(1, file.classes.size());

        ClassStatement classStatement = file.classes.getFirst();
        assertEquals("Test", classStatement.name);
        assertEquals("\\A", classStatement.parentClasses.getFirst().name);
        assertEquals("\\B\\A", classStatement.parentClasses.get(1).name);
        assertEquals(1, classStatement.properties.size());
        assertEquals(1, classStatement.functions.size());
    }
}
