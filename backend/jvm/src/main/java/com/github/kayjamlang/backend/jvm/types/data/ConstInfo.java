package com.github.kayjamlang.backend.jvm.types.data;

import com.github.kayjamlang.core.opcodes.AccessType;

public class ConstInfo {
    public final String name;
    public final AccessType accessType;

    public String type;

    public ConstInfo(String name, String type, AccessType accessType) {
        this.name = name;
        this.type = type;
        this.accessType = accessType;
    }
}
