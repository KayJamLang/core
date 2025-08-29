package com.github.kayjamlang.backend.jvm.generators;

import com.github.kayjamlang.backend.jvm.SymbolRegistry;
import com.github.kayjamlang.backend.jvm.utils.NamesUtils;
import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.statements.ClassStatement;
import org.objectweb.asm.ClassWriter;

import java.util.HashMap;
import java.util.Map;

public class JVMCodeGenerator {
    private final SymbolRegistry symbolRegistry;

    public JVMCodeGenerator(SymbolRegistry symbolRegistry) {
        this.symbolRegistry = symbolRegistry;
    }

    public Map<String, byte[]> generate(KayJamFile file) {
        Map<String, byte[]> compiledClasses = new HashMap<>();
        String packageName = NamesUtils.normalizeNamespace(file.namespace);

        for (ClassStatement classStatement : file.classes) {
            ClassGenerator classGenerator = new ClassGenerator(file, classStatement, packageName, symbolRegistry);
            ClassWriter cw = classGenerator.generate();
            String className = packageName + "/" + classStatement.name;
            compiledClasses.put(className, cw.toByteArray());
        }

        return compiledClasses;
    }
}