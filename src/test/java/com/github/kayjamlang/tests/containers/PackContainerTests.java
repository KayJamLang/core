package com.github.kayjamlang.tests.containers;

import com.github.kayjamlang.core.Stmt;
import com.github.kayjamlang.core.containers.PackContainer;
import com.github.kayjamlang.core.exceptions.LexerException;
import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PackContainerTests {
    @Test
    public void constantTest() throws ParserException, LexerException {
        Stmt stmt = TestsUtils.parseStmt("pack Test\\A {" +
                "const test = 123;" +
                "}");

        assertEquals(PackContainer.class, stmt.getClass());

        PackContainer pack = (PackContainer) stmt;
        assertEquals(1, pack.constants.size());
        assertEquals(123, pack.constants.get("test"));
    }
}
