package com.github.kayjamlang.backend.jvm.utils;

import com.github.kayjamlang.core.opcodes.AccessType;
import org.objectweb.asm.Opcodes;

public class AccessUtils {
    public static int accessIntByAccessType(AccessType accessType) {
        return switch (accessType) {
            case INTERNAL -> 0;
            case PRIVATE -> Opcodes.ACC_PRIVATE;
            case PROTECTED -> Opcodes.ACC_PROTECTED;
            case PUBLIC, NONE -> Opcodes.ACC_PUBLIC;
        };
    }
}
