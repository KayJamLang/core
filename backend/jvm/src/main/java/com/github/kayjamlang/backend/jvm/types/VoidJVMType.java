package com.github.kayjamlang.backend.jvm.types;

import com.github.kayjamlang.core.expressions.data.Operation;

public class VoidJVMType extends JVMType {
    public static final VoidJVMType INSTANCE = new VoidJVMType();

    @Override
    public String getDescriptor() {
        return "V";
    }

    @Override
    protected int operationBytecode(Operation operation) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    protected JVMType operation(Operation operation, JVMType rightValue) {
        throw new UnsupportedOperationException("Operation not supported");
    }
}
