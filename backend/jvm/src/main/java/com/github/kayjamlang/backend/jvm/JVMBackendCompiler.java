package com.github.kayjamlang.backend.jvm;

import com.github.kayjamlang.backend.IBackendOptions;
import com.github.kayjamlang.backend.IBackendCompiler;
import com.github.kayjamlang.backend.IOptions;
import com.github.kayjamlang.backend.tree.KayJamFileTree;
import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JVMBackendCompiler implements IBackendCompiler {
    public static final JVMBackendCompiler INSTANCE = new JVMBackendCompiler();

    @Override
    public void addOptions(Options options) {

    }

    @Override
    public IBackendOptions parseOptions(CommandLine data) {
        return null;
    }

    @Override
    public void compile(IOptions options) throws KayJamLexerException, KayJamParserException, IOException {
        List<KayJamFile> files = KayJamFileTree.loadFilesFromPath(options.getInputDir().toPath());
        for (KayJamFile file : files) {
            JVMKayJamExpressionVisitor visitor = new JVMKayJamExpressionVisitor();
            visitor.visitKayJamFile(file);
            for (Map.Entry<String, ClassWriter> entry : visitor.getClasses().entrySet()) {
                File outputFile = getFile(options, entry);

                if (outputFile.createNewFile()) {
                    FileOutputStream fos = new FileOutputStream(outputFile);
                    fos.write(entry.getValue().toByteArray());
                    fos.close();
                }
            }
        }
    }

    private static File getFile(IOptions options, Map.Entry<String, ClassWriter> entry) throws IOException {
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
