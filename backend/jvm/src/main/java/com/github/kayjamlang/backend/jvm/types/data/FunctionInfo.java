package com.github.kayjamlang.backend.jvm.types.data;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.List;

public class FunctionInfo {
    public final String name;
    public final List<Type> arguments;
    public final Type returnType;
    public final AccessType accessType;

    public FunctionInfo(String name, List<Type> arguments, Type returnType, AccessType accessType) {
        this.name = name;
        this.arguments = arguments;
        this.returnType = returnType;
        this.accessType = accessType;
    }
}