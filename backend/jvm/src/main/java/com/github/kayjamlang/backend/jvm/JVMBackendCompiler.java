package com.github.kayjamlang.backend.jvm;

import com.github.kayjamlang.backend.IBackendOptions;
import com.github.kayjamlang.backend.IBackendCompiler;
import com.github.kayjamlang.backend.IOptions;
import com.github.kayjamlang.backend.jvm.generators.JVMCodeGenerator;
import com.github.kayjamlang.backend.jvm.impl.ASMClasspathClassProviderImpl;
import com.github.kayjamlang.backend.jvm.passes.SuperclassResolutionPass;
import com.github.kayjamlang.backend.jvm.passes.TypeInferencePass;
import com.github.kayjamlang.backend.tree.KayJamFileTree;
import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.statements.ClassStatement;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JVMBackendCompiler implements IBackendCompiler {
    public static final JVMBackendCompiler INSTANCE = new JVMBackendCompiler();

    @Override
    public IBackendOptions createOptionsClass() {
        return new JVMBackendOptions();
    }

    @Override
    public void compile(IOptions options) throws KayJamLexerException, KayJamParserException, IOException {
        String classpath = System.getProperty("java.class.path");
        List<File> classpathFiles = classpath == null || classpath.isEmpty() ?
                List.of():
                Arrays.stream(classpath.split(File.pathSeparator))
                .map(File::new)
                .toList();

        List<KayJamFile> files = KayJamFileTree.loadFilesFromPath(options.getInputDir().toPath());
        SymbolRegistry symbolRegistry = new SymbolRegistry(new ASMClasspathClassProviderImpl(classpathFiles));
        SignatureCollectorVisitor signatureCollectorVisitor = new SignatureCollectorVisitor(symbolRegistry);
        for (KayJamFile file : files) {
            signatureCollectorVisitor.visitKayJamFile(file);
        }

        SuperclassResolutionPass superclassResolver = new SuperclassResolutionPass(symbolRegistry);
        superclassResolver.resolveSuperclassesAndInterfaces();

        TypeInferencePass typeInference = new TypeInferencePass(symbolRegistry);
        typeInference.inferTypes();

        JVMCodeGenerator codeGenerator = new JVMCodeGenerator(symbolRegistry);
        for (KayJamFile file : files) {
            for (Map.Entry<String, byte[]> entry : codeGenerator.generate(file).entrySet()) {
                File outputFile = getFile(options, entry);

                if (outputFile.createNewFile()) {
                    FileOutputStream fos = new FileOutputStream(outputFile);
                    fos.write(entry.getValue());
                    fos.close();
                }
            }
        }
    }

    private static File getFile(IOptions options, Map.Entry<String, byte[]> entry) throws IOException {
        File outputFile = new File(options.getOutputDir(), "./" + entry.getKey() + ".class");
        if (outputFile.exists()) {
            if (!outputFile.delete()) {
                throw new IOException("Could not delete output file: " + outputFile.getAbsolutePath());
            }
        }

        if (!outputFile.getParentFile().exists() && !outputFile.getParentFile().mkdirs()) {
            throw new IOException("Could not create directory " + outputFile.getParentFile());
        }

        return outputFile;
    }
}
