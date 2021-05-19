package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.exceptions.LexerException;
import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.UseExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UseExpressionTests {
    @Test
    public void parseSingleUse() throws ParserException, LexerException {
        Expression expression = TestsUtils.parse("use Test\\Value from \"test.kj\"");
        assertEquals(UseExpression.class, expression.getClass());

        UseExpression useExpression = (UseExpression) expression;
        assertEquals("test.kj", useExpression.from);

        assertEquals(1, useExpression.required.size());
        assertEquals("\\Test\\Value", useExpression.required.get(0));
    }

    @Test
    public void parseMultiSingleUse() throws ParserException, LexerException {
        Expression expression = TestsUtils.parse("use Test{ Value, ValueT } from \"test.kj\"");
        assertEquals(UseExpression.class, expression.getClass());

        UseExpression useExpression = (UseExpression) expression;
        assertEquals("test.kj", useExpression.from);

        assertEquals(2, useExpression.required.size());
        assertEquals("\\Test\\Value", useExpression.required.get(0));
        assertEquals("\\Test\\ValueT", useExpression.required.get(1));
    }

    @Test
    public void parseMultiUse() throws ParserException, LexerException {
        Expression expression = TestsUtils.parse("use { Value, ValueT } from \"test.kj\"");
        assertEquals(UseExpression.class, expression.getClass());

        UseExpression useExpression = (UseExpression) expression;
        assertEquals("test.kj", useExpression.from);

        assertEquals(2, useExpression.required.size());
        assertEquals("\\Value", useExpression.required.get(0));
        assertEquals("\\ValueT", useExpression.required.get(1));
    }
}