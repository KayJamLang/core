package com.github.kayjamlang.backend.ts;

import com.github.kayjamlang.backend.jvm.JVMKayJamExpressionVisitor;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.expressions.AccessExpression;
import com.github.kayjamlang.core.expressions.VariableLinkExpression;
import org.junit.Test;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
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
                "    const ROLE_CAN_MANAGE_INVITES = 1 << 5;\n" +
                "}");

        parser.fillFile();

        JVMKayJamExpressionVisitor visitor = new JVMKayJamExpressionVisitor();
        visitor.visitKayJamFile(parser.file);

        for (Map.Entry<String, ClassWriter> entry : visitor.getClasses().entrySet()) {
            File file = new File("out/"+entry.getKey()+".class");
            file.getParentFile().mkdirs();
            if(file.exists()) {
                file.delete();
            }

            if (file.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(entry.getValue().toByteArray());
                fos.close();
            }
        }
    }
}
