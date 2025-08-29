package com.github.kayjamlang.backend.jvm;

import com.github.kayjamlang.backend.jvm.types.ClassJVMType;

public interface ClasspathClassProvider {
    ClassJVMType loadClass(SymbolRegistry symbolRegistry, String javaFQN);
}