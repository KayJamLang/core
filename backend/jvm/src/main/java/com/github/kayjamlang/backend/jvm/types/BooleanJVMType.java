package com.github.kayjamlang.backend.jvm.types;

import com.github.kayjamlang.core.expressions.data.Operation;
import org.objectweb.asm.Opcodes;

public class BooleanJVMType extends JVMType {
    public static final BooleanJVMType INSTANCE = new BooleanJVMType();

    @Override
    public String getDescriptor() {
        return "Z";
    }

    @Override
    public JVMType operation(Operation operation, JVMType rightValue) {
        switch (operation) {
            case AND:
            case OR:
                return this;
        }

        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public int operationBytecode(Operation operation) {
        switch (operation) {
            case AND:
                return Opcodes.IAND;
            case OR:
                return Opcodes.IOR;
        }

        throw new UnsupportedOperationException("Operation not supported");
    }
}
