package com.github.kayjamlang.backend.jvm.types.data;

import com.github.kayjamlang.backend.jvm.types.JVMType;
import com.github.kayjamlang.core.expressions.data.Property;
import com.github.kayjamlang.core.opcodes.AccessType;

public class PropertyInfo {
    public final String name;
    public final AccessType accessType;
    public JVMType type;
    public Property sourceProperty;

    public PropertyInfo(String name, JVMType type, AccessType accessType, Property sourceProperty) {
        this.name = name;
        this.type = type;
        this.accessType = accessType;
        this.sourceProperty = sourceProperty;
    }
}
