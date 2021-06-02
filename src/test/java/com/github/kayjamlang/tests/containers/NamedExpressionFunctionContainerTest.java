package com.github.kayjamlang.tests.containers;

import com.github.kayjamlang.core.Stmt;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.NamedExpressionFunctionContainer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class NamedExpressionFunctionContainerTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("named fun test{}"));
    }

    @Test
    public void test() throws Exception {
        Stmt stmt = parser.readStmt();

        assertNotNull(stmt);
        assertSame(NamedExpressionFunctionContainer.class, stmt.getClass());

        NamedExpressionFunctionContainer namedExpressionFunction = (NamedExpressionFunctionContainer) stmt;
        assertEquals(0, namedExpressionFunction.children.size());
    }
}
