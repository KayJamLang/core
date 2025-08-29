package com.github.kayjamlang.tests;

import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.parser.KayJamParser;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.expressions.ValueExpression;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileParseTest {

    @Test(timeout = 5000)
    public void parse() throws KayJamLexerException, KayJamParserException {
        KayJamParser parser = TestsUtils.getParser("namespace \\test\\y;" +
                "use Test\\Test;" +
                "const test = 1;" +
                "println(123);");

        parser.fillFile();

        KayJamFile file = parser.file;
        assertEquals("\\test\\y", file.namespace);

        assertEquals(1, file.usages.size());
        assertEquals("\\Test\\Test", file.usages.getFirst().usedImport.name);

        assertEquals(1, file.constants.size());
        assertEquals("test", file.constants.getFirst().name);
        assertEquals(ValueExpression.class, file.constants.getFirst().value.getClass());
    }
}
