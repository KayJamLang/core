package com.github.kayjamlang.tests.containers.functions;

import com.github.kayjamlang.core.Stmt;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.FunctionContainer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionContainerZeroArgsTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("fun test(){true;false}"));
    }

    @Test
    public void test() throws Exception {
        Stmt stmt = parser.readStmt();

        assertNotNull(stmt);
        assertSame(FunctionContainer.class, stmt.getClass());

        FunctionContainer functionContainer = (FunctionContainer) stmt;
        assertEquals(0, functionContainer.arguments.size());
        assertEquals(2, functionContainer.children.size());
    }
}