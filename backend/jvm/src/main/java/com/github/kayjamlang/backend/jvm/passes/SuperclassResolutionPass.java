package com.github.kayjamlang.backend.jvm.passes;

import com.github.kayjamlang.backend.jvm.SymbolRegistry;
import com.github.kayjamlang.backend.jvm.types.ClassJVMType;
import com.github.kayjamlang.backend.jvm.types.JVMType;
import com.github.kayjamlang.core.Type;

public class SuperclassResolutionPass {
    private final SymbolRegistry symbolRegistry;

    public SuperclassResolutionPass(SymbolRegistry symbolRegistry) {
        this.symbolRegistry = symbolRegistry;
    }

    public void resolveSuperclassesAndInterfaces() {
        for (ClassJVMType classInfo : symbolRegistry.classes.values()) {
            if (classInfo.fileContext == null) {
                throw new IllegalStateException("File context not found for KayJam-defined class: " + classInfo.name);
            }

            for (Type type : classInfo.parentTypes) {
                JVMType parentJVMType = symbolRegistry.resolveType(type, classInfo.fileContext);
                if (parentJVMType instanceof ClassJVMType) {
                    ClassJVMType parent = (ClassJVMType) parentJVMType;
                    if (parent.type == ClassJVMType.ClassType.CLASS) {
                        if (classInfo.superType != null) {
                            throw new IllegalStateException("Class can has only one superclass: " + classInfo.name);
                        }

                        classInfo.superType = parent;
                    } else if (parent.type == ClassJVMType.ClassType.INTERFACE) {
                        classInfo.interfaces.add(parent);
                    }
                } else {
                    throw new IllegalStateException("Parent type of " + parentJVMType + " is not a class");
                }
            }
        }
    }
}