package com.github.kayjamlang.backend.jvm;

import com.github.kayjamlang.backend.jvm.types.*;
import com.github.kayjamlang.backend.jvm.utils.NamesUtils;
import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolRegistry {
    public final Map<String, ClassJVMType> classes = new HashMap<>();
    public final Map<String, ClassJVMType> externalClassesCache = new HashMap<>();

    private final ClasspathClassProvider classpathClassProvider;

    public SymbolRegistry(ClasspathClassProvider classpathClassProvider) {
        this.classpathClassProvider = classpathClassProvider;
    }


    public void registerClass(ClassJVMType classInfo) {
        if (classes.containsKey(classInfo.name)) {
            throw new IllegalStateException("Class with FQKN '" + classInfo.name + "' already registered.");
        }

        classes.put(classInfo.name, classInfo);
    }

    public ClassJVMType findClass(String name) {
        ClassJVMType classJVMType = classes.get(name);
        if(classJVMType == null) {
            classJVMType = externalClassesCache.get(name);
        }

        if (classJVMType != null) {
            return classJVMType;
        }

        String javaFQN = NamesUtils.convertSomeFQNToJavaFQN(name);
        ClassJVMType externalClassInfo = classpathClassProvider.loadClass(this, javaFQN);
        if (externalClassInfo != null) {
            externalClassesCache.put(name, externalClassInfo);
            return externalClassInfo;
        }

        return null;
    }

    public JVMType resolveType(Type type, KayJamFile fileContext) {
        if(type.equals(Type.INTEGER)) return IntJVMType.INSTANCE;
        else if(type.equals(Type.DOUBLE)) return DoubleJVMType.INSTANCE;
        else if(type.equals(Type.LONG)) return LongJVMType.INSTANCE;
        else if(type.equals(Type.STRING)) return StringJVMType.INSTANCE;
        else if(type.equals(Type.BOOLEAN)) return BooleanJVMType.INSTANCE;
        else if(type.equals(Type.ANY)) return AnyJVMType.INSTANCE;
        else if(type.equals(Type.VOID) || type.equals(Type.NOTHING)) return VoidJVMType.INSTANCE;

        String resolvedKayJamFQN = resolveKayJamName(type.name, fileContext.namespace, fileContext.usages);
        ClassJVMType classJVMType = findClass(resolvedKayJamFQN);
        if(classJVMType == null) {
            throw new IllegalArgumentException(
                    "Unknown class or type '" + type.name + "' (resolved to '" + resolvedKayJamFQN + "') " +
                            "in file '" + fileContext.path + "' namespace '" + fileContext.namespace + "'."
            );
        }

        return classJVMType;
    }

    public String resolveKayJamName(String name, String currentNamespace, List<KayJamFile.Usage> usages) {
        if (name.startsWith("\\")) {
            if (findClass(name) != null) {
                return name;
            }
        }

        for (KayJamFile.Usage usage : usages) {
            String aliasToMatch = usage.typealias;
            if (aliasToMatch == null || aliasToMatch.isEmpty()) {
                aliasToMatch = NamesUtils.getSimpleNameFromFQN(usage.usedImport.name);
            }

            if (name.equals(aliasToMatch)) {
                String resolvedUsedImportFQN = resolveKayJamName(usage.usedImport.name, null, usages);
                if (resolvedUsedImportFQN != null && findClass(resolvedUsedImportFQN) != null) {
                    return resolvedUsedImportFQN;
                }
            }
        }

        String candidateFQN;
        if (currentNamespace != null && !currentNamespace.isEmpty() && !currentNamespace.equals("\\")) {
            candidateFQN = currentNamespace + name;
        } else {
            candidateFQN = name;
        }

        if (findClass(candidateFQN) != null) {
            return candidateFQN;
        }

        return name;
    }
}
