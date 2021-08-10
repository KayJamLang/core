package com.github.kayjamlang.tests;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.LexerException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;

public class VoidTypeTest {
    private static KayJamParser parser;

    @BeforeClass
    public static void prepare() throws LexerException {
        parser = new KayJamParser(new KayJamLexer("void"));
        parser.moveAhead();
    }

    @Test
    public void test() throws Exception {
        Type type = parser.parseType(false);
        assertNotEquals(Type.VOID, type);
        assertFalse(type.nullable);
    }
}
