package com.github.kayjamlang.backend.jvm.types;

import com.github.kayjamlang.core.expressions.data.Operation;
import org.objectweb.asm.Opcodes;

public class IntJVMType extends NumberJVMType {
    public static final IntJVMType INT = new IntJVMType();

    @Override
    public String getDescriptor() {
        return "I";
    }

    @Override
    protected int operationBytecode(Operation operation) {
        switch (operation) {
            case PLUS:
                return Opcodes.IADD;
            case MINUS:
                return Opcodes.ISUB;
            case MULTIPLY:
                return Opcodes.IMUL;
            case DIVISION:
                return Opcodes.IDIV;
            case BITWISE_OR:
                return Opcodes.IOR;
            case BITWISE_AND:
                return Opcodes.IAND;
            case BITWISE_SHIFT_LEFT:
                return Opcodes.ISHL;
            case BITWISE_SHIFT_RIGHT:
                return Opcodes.ISHR;
        }

        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
