package com.github.kayjamlang.backend.jvm.types;

import com.github.kayjamlang.core.expressions.data.Operation;
import org.objectweb.asm.Opcodes;

public class DoubleJVMType extends NumberJVMType {
    public static final DoubleJVMType INSTANCE = new DoubleJVMType();

    @Override
    public String getDescriptor() {
        return "D";
    }

    @Override
    protected int operationBytecode(Operation operation) {
        switch (operation) {
            case PLUS:
                return Opcodes.DADD;
            case MINUS:
                return Opcodes.DSUB;
            case MULTIPLY:
                return Opcodes.DMUL;
            case DIVISION:
                return Opcodes.DDIV;
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

    @Override
    public int getPriority() {
        return 9;
    }
}
