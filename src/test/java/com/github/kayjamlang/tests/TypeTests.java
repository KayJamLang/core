package com.github.kayjamlang.tests;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import org.junit.Test;

import static org.junit.Assert.*;

public class TypeTests {

    @Test
    public void nullable() throws Exception {
        Type type = TestsUtils.getParser("string?").parseType(false);
        assertEquals(Type.STRING, type);
        assertTrue(type.nullable);
    }

    @Test
    public void primitiveTypeParse() throws KayJamParserException, KayJamLexerException {
        Type type = TestsUtils.getParser("int")
                .parseType(false);
        assertEquals(Type.INTEGER, type);
    }

    @Test
    public void primitiveTypeForFunctionParse() throws KayJamParserException, KayJamLexerException {
        Type type = TestsUtils.getParser("void")
                .parseType(true);
        assertEquals(Type.VOID, type);
    }

    @Test
    public void typeParse() throws KayJamParserException, KayJamLexerException {
        Type type = TestsUtils.getParser("test")
                .parseType(true);
        assertEquals("\\test", type.name);
    }

    @Test
    public void multiTypeParse() throws KayJamParserException, KayJamLexerException {
        Type type = TestsUtils.getParser("test\\testType")
                .parseType(true);
        assertEquals("\\test\\testType", type.name);
    }

    @Test
    public void multiTypeParseWithStart() throws KayJamParserException, KayJamLexerException {
        Type type = TestsUtils.getParser("\\test\\testType")
                .parseType(true);
        assertEquals("\\test\\testType", type.name);
    }

    @Test
    public void primitiveTypeForFunctionParseException() throws KayJamParserException, KayJamLexerException {
        Type type = TestsUtils.getParser("void").parseType(false);
        assertNotEquals(Type.VOID, type);
    }
}
