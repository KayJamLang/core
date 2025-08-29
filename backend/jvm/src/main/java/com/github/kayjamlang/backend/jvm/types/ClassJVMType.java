package com.github.kayjamlang.backend.jvm.types;

import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Operation;

import java.util.HashSet;
import java.util.Set;

public class ClassJVMType extends JVMType {
    public enum ClassType {
        INTERFACE,
        OBJECT,
        CLASS
    }

    public final String name;
    public final ClassType type;
    public final Set<Type> parentTypes = new HashSet<>();

    public ClassJVMType superType = null;
    public final Set<ClassJVMType> interfaces = new HashSet<>();

    public KayJamFile fileContext;

    public ClassJVMType(
            String name,
            ClassType type
    ) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getDescriptor() {
        String javaFQN = name.startsWith("\\") ? name.substring(1).replace("\\", ".") : name;
        return "L" + javaFQN.replace('.', '/') + ";";
    }

    @Override
    protected JVMType operation(Operation operation, JVMType rightValue) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    protected int operationBytecode(Operation operation) {
        throw new UnsupportedOperationException("Operation not supported");
    }
}
