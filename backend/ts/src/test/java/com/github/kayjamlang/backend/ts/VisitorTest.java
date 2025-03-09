package com.github.kayjamlang.backend.ts;

import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.expressions.AccessExpression;
import com.github.kayjamlang.core.expressions.VariableLinkExpression;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VisitorTest {
    @Test
    public void kjFileExpressionTest() throws KayJamLexerException, KayJamParserException {
        KayJamParser parser = TestsUtils.getParser("namespace \\Zation;\n" +
                "\n" +
                "object ThreadRoles {\n" +
                "    const ROLE_CAN_MANAGE_ROLES = 1 << 1;\n" +
                "    const ROLE_CAN_EDIT_THREAD = 1 << 2;\n" +
                "    const ROLE_CAN_CHANGE_AVATAR = 1 << 3;\n" +
                "    const ROLE_CAN_DELETE_POSTS = 1 << 4;\n" +
                "    const ROLE_CAN_MANAGE_INVITES = 1 << 5;\n" +
                "}");

        parser.fillFile();

        JSKayJamExpressionVisitor visitor = new JSKayJamExpressionVisitor();
        String result = visitor.visitKayJamFile(parser.file);

        System.out.println(result);
    }

    @Test
    public void accessExpressionTest() {
        JSKayJamExpressionVisitor visitor = new JSKayJamExpressionVisitor();
        String result = visitor.visit(
                new AccessExpression(
                        new VariableLinkExpression("test", 0),
                        new VariableLinkExpression("test", 0),
                        0
                )
        );

        System.out.println(result);
        assertEquals("test.test", result);
    }

    @Test
    public void variableLinkExpressionTest() {
        JSKayJamExpressionVisitor visitor = new JSKayJamExpressionVisitor();
        String result = visitor.visit(
                new VariableLinkExpression("test", 0)
        );

        System.out.println(result);
        assertEquals("test", result);
    }
}
