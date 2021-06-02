package com.github.kayjamlang.tests.containers.classes;

import com.github.kayjamlang.core.Stmt;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.ClassContainer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CloneClassContainerTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("class Test {var test = 123;fun test(){return test}}"));
    }

    @Test
    public void test() throws Exception {
        Stmt stmt = parser.readStmt();

        assertNotNull(stmt);
        assertSame(ClassContainer.class, stmt.getClass());

        ClassContainer classContainer = (ClassContainer) stmt;
        assertEquals("Test", classContainer.name);
        assertNull(classContainer.extendsClass);
        assertEquals(0, classContainer.implementsClass.size());
        assertEquals(1, classContainer.variables.size());
        assertEquals(1, classContainer.functions.size());

        ClassContainer classContainer1 = classContainer.clone();
        assertNotEquals(classContainer, classContainer1);
        assertEquals(classContainer.name, classContainer1.name);
        assertNull(classContainer1.extendsClass);
        assertEquals(classContainer.implementsClass.size(), classContainer1.implementsClass.size());
        assertEquals(classContainer.functions.size(), classContainer1.functions.size());
    }
}