package com.github.kayjamlang.tests;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.LexerException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class NullableTypeTest {
    private static KayJamParser parser;

    @BeforeClass
    public static void prepare() {
        parser = new KayJamParser(new KayJamLexer("string?"));
    }

    @Test
    public void test() throws Exception {
        Type type = parser.parseType(false);
        assertEquals(Type.STRING, type);
        assertTrue(type.nullable);
    }
}
