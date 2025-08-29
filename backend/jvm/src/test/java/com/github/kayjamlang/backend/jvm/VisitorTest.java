package com.github.kayjamlang.backend.jvm;

import com.github.kayjamlang.backend.jvm.generators.JVMCodeGenerator;
import com.github.kayjamlang.backend.jvm.impl.ASMClasspathClassProviderImpl;
import com.github.kayjamlang.backend.jvm.passes.SuperclassResolutionPass;
import com.github.kayjamlang.backend.jvm.passes.TypeInferencePass;
import com.github.kayjamlang.core.parser.KayJamParser;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.statements.ClassStatement;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class VisitorTest {
    public static List<File> buildClasspathFromSystemProperty() {
        String classpath = System.getProperty("java.class.path");
        if (classpath == null || classpath.isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(classpath.split(File.pathSeparator))
                .map(File::new)
                .collect(Collectors.toList());
    }

    @Test
    public void kjFileExpressionTest() throws KayJamLexerException, KayJamParserException, IOException {
        KayJamParser parser = TestsUtils.getParser("namespace \\test;\n" +
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
                "};" +
                "class A {};" +
                "class Test: \\java\\lang\\System {" +
                " const Test = 1;" +
                " prop tt = 2;" +
                " fun test(): ThreadRoles {" +
                "   " +
                " }" +
                "}");

        parser.fillFile();

        SymbolRegistry symbolRegistry = new SymbolRegistry(
                new ASMClasspathClassProviderImpl(buildClasspathFromSystemProperty())
        );
        SignatureCollectorVisitor signatureCollectorVisitor = new SignatureCollectorVisitor(symbolRegistry);
        signatureCollectorVisitor.visitKayJamFile(parser.file);

        SuperclassResolutionPass superclassResolver = new SuperclassResolutionPass(symbolRegistry);
        superclassResolver.resolveSuperclassesAndInterfaces();

        TypeInferencePass typeInference = new TypeInferencePass(symbolRegistry);
        typeInference.inferTypes();

        JVMCodeGenerator codeGenerator = new JVMCodeGenerator(symbolRegistry);
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
