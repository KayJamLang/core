package com.github.kayjamlang.backend.ts;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TSBackendCompiler implements IBackendCompiler {
    public static final TSBackendCompiler INSTANCE = new TSBackendCompiler();

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
            JSKayJamExpressionVisitor jsVisitor = new JSKayJamExpressionVisitor();
            DTSKayJamExpressionVisitor dtsVisitor = new DTSKayJamExpressionVisitor();
            File jsOutputFile = getFile(options, "index.js");
            File dtsOutputFile = getFile(options, "index.d.ts");

            String jsOutput = jsVisitor.visitKayJamFile(file);
            String dtsOutput = dtsVisitor.visitKayJamFile(file);

            FileOutputStream fos = new FileOutputStream(jsOutputFile);
            fos.write(jsOutput.getBytes());
            fos.close();

            fos = new FileOutputStream(dtsOutputFile);
            fos.write(dtsOutput.getBytes());
            fos.close();
        }
    }

    private static File getFile(IOptions options, String file) throws IOException {
        File outputFile = new File(options.getOutputDir(), "./" + file);
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
