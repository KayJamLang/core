package com.github.kayjamlang.tests;

import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.expressions.ValueExpression;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileParseTest {

    @Test
    public void parse() throws KayJamLexerException, KayJamParserException {
        KayJamParser parser = TestsUtils.getParser("namespace \\test\\y;" +
                "use Test\\Test from \"file\";" +
                "const test = 1;" +
                "println(123);");

        parser.fillFile();

        KayJamFile file = parser.file;
        assertEquals("\\test\\y", file.namespace);

        assertEquals(1, file.usages.size());
        assertEquals("file", file.usages.get(0).from);
        assertEquals(1, file.usages.get(0).required.size());
        assertEquals("\\Test\\Test", file.usages.get(0).required.get(0));

        assertEquals(1, file.constants.size());
        assertEquals(ValueExpression.class, file.constants.get("test").getClass());
    }
}
