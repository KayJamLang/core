package com.github.kayjamlang.tests.containers;

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
        Expression expression = TestsUtils.parse("pack Test\\A {" +
                "const test = 123;" +
                "}");

        assertEquals(PackContainer.class, expression.getClass());

        PackContainer pack = (PackContainer) expression;
        assertEquals(1, pack.constants.size());
        assertEquals(123, pack.constants.get("test"));
    }
}
