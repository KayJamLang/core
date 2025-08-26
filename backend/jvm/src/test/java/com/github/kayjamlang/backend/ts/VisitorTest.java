package com.github.kayjamlang.backend.ts;

import com.github.kayjamlang.backend.jvm.generators.JVMCodeGenerator;
import com.github.kayjamlang.core.parser.KayJamParser;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VisitorTest {
    @Test
    public void kjFileExpressionTest() throws KayJamLexerException, KayJamParserException, IOException {
        KayJamParser parser = TestsUtils.getParser("namespace \\Zation;\n" +
                "\n" +
                "object ThreadRoles {\n" +
                "    const ROLE_CAN_MANAGE_ROLES = 1 << 1;\n" +
                "    const ROLE_CAN_EDIT_THREAD = 1 << 2;\n" +
                "    const ROLE_CAN_CHANGE_AVATAR = 1 << 3;\n" +
                "    const ROLE_CAN_DELETE_POSTS = 1 << 4;\n" +
                "    private const ROLE_CAN_MANAGE_INVITES = 1 << 5;\n" +
                "       " +
                "    prop someVariable = 1;" +
                "    private prop somePrivateVariable = 2;" +
                "    internal prop someInternalVariable = 2;" +
                "}" +
                "class Test {" +
                " const Test = 1;" +
                " prop tt = 2;" +
                " fun helloWorld(): void {" +
                "   return 123;" +
                " }" +
                "}");

        parser.fillFile();

        JVMCodeGenerator codeGenerator = new JVMCodeGenerator();
        for (Map.Entry<String, byte[]> entry : codeGenerator.generate(parser.file).entrySet()) {
            File file = new File("out/"+entry.getKey()+".class");
            file.getParentFile().mkdirs();
            if(file.exists()) {
                file.delete();
            }

            if (file.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(entry.getValue());
                fos.close();
            }
        }
    }
}
