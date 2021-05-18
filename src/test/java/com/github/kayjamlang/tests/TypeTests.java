package com.github.kayjamlang.tests;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.LexerException;
import com.github.kayjamlang.core.exceptions.ParserException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TypeTests {

    @Test
    public void primitiveTypeParse() throws ParserException, LexerException {
        Type type = TestsUtils.getParser("int")
                .parseType(false);
        assertEquals(Type.INTEGER, type);
    }

    @Test
    public void primitiveTypeForFunctionParse() throws ParserException, LexerException {
        Type type = TestsUtils.getParser("void")
                .parseType(true);
        assertEquals(Type.VOID, type);
    }

    @Test
    public void typeParse() throws ParserException, LexerException {
        Type type = TestsUtils.getParser("test")
                .parseType(true);
        assertEquals("\\test", type.name);
    }

    @Test
    public void multiTypeParse() throws ParserException, LexerException {
        Type type = TestsUtils.getParser("test\\testType")
                .parseType(true);
        assertEquals("\\test\\testType", type.name);
    }

    @Test
    public void multiTypeParseWithStart() throws ParserException, LexerException {
        Type type = TestsUtils.getParser("\\test\\testType")
                .parseType(true);
        assertEquals("\\test\\testType", type.name);
    }

    @Test
    public void primitiveTypeForFunctionParseException() throws ParserException, LexerException {
        Type type = TestsUtils.getParser("void").parseType(false);
        assertNotEquals(Type.VOID, type);
    }
}
