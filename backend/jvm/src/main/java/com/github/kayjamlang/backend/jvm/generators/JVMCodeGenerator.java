package com.github.kayjamlang.backend.jvm.generators;

import com.github.kayjamlang.backend.jvm.utils.JVMNameUtils;
import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.statements.ClassStatement;
import org.objectweb.asm.ClassWriter;

import java.util.HashMap;
import java.util.Map;

public class JVMCodeGenerator {
    public Map<String, byte[]> generate(KayJamFile file) {
        Map<String, byte[]> compiledClasses = new HashMap<>();
        String packageName = JVMNameUtils.normalizeNamespace(file.namespace);

        for (ClassStatement classStatement : file.classes) {
            ClassGenerator classGenerator = new ClassGenerator(classStatement, packageName);
            ClassWriter cw = classGenerator.generate();
            String className = packageName + "/" + JVMNameUtils.normalizeClassName(classStatement.name);
            compiledClasses.put(className, cw.toByteArray());
        }

        return compiledClasses;
    }
}