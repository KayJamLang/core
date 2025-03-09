package com.github.kayjamlang.backend.ts;

import com.github.kayjamlang.backend.IBackendOptions;
import com.github.kayjamlang.backend.IBackendCompiler;
import com.github.kayjamlang.backend.IOptions;
import com.github.kayjamlang.backend.tree.KayJamFileTree;
import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TSBackendCompiler implements IBackendCompiler {
    public static final TSBackendCompiler INSTANCE = new TSBackendCompiler();

    @Override
    public IBackendOptions createOptionsClass() {
        return new TSBackendOptions();
    }

    @Override
    public void compile(IOptions options) throws KayJamLexerException, KayJamParserException, IOException {
        List<KayJamFile> files = KayJamFileTree.loadFilesFromPath(options.getInputDir().toPath());
        File jsOutputFile = getFile(options, "index.js");
        File dtsOutputFile = getFile(options, "index.d.ts");

        StringBuilder jsOutput = new StringBuilder();
        StringBuilder dtsOutput = new StringBuilder();
        for (KayJamFile file : files) {
            JSKayJamExpressionVisitor jsVisitor = new JSKayJamExpressionVisitor();
            DTSKayJamExpressionVisitor dtsVisitor = new DTSKayJamExpressionVisitor();

            jsOutput.append(jsVisitor.visitKayJamFile(file));
            dtsOutput.append(dtsVisitor.visitKayJamFile(file));
        }

        FileOutputStream fos = new FileOutputStream(jsOutputFile);
        fos.write(jsOutput.toString().getBytes());
        fos.close();

        fos = new FileOutputStream(dtsOutputFile);
        fos.write(dtsOutput.toString().getBytes());
        fos.close();
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
