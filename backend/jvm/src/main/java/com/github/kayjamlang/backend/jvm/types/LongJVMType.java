package com.github.kayjamlang.backend.jvm.types;

import com.github.kayjamlang.core.expressions.data.Operation;
import org.objectweb.asm.Opcodes;

public class LongJVMType extends NumberJVMType {
    public static final LongJVMType INSTANCE = new LongJVMType();

    @Override
    public String getDescriptor() {
        return "J";
    }

    @Override
    protected int getPriority() {
        return 1;
    }

    @Override
    protected int operationBytecode(Operation operation) {
        switch (operation) {
            case PLUS:
                return Opcodes.LADD;
            case MINUS:
                return Opcodes.LSUB;
            case MULTIPLY:
                return Opcodes.LMUL;
            case DIVISION:
                return Opcodes.LDIV;
            case BITWISE_OR:
                return Opcodes.LOR;
            case BITWISE_AND:
                return Opcodes.LAND;
            case BITWISE_SHIFT_LEFT:
                return Opcodes.LSHL;
            case BITWISE_SHIFT_RIGHT:
                return Opcodes.LSHR;
        }

        throw new UnsupportedOperationException("Operation not supported");
    }
}
