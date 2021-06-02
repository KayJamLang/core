package com.github.kayjamlang.tests.containers;

import com.github.kayjamlang.core.Stmt;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContainerEmptyTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("{}"));
    }

    @Test
    public void test() throws Exception {
        Stmt stmt = parser.readStmt();

        assertNotNull(stmt);
        assertSame(Container.class, stmt.getClass());

        Container container = (Container) stmt;
        assertEquals(0, container.children.size());
    }
}