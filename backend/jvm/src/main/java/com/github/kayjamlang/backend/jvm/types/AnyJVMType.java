package com.github.kayjamlang.backend.jvm.types;

import com.github.kayjamlang.core.expressions.data.Operation;

public class AnyJVMType extends JVMType {
    public static final AnyJVMType INSTANCE = new AnyJVMType();

    @Override
    public String getDescriptor() {
        return "Ljava/lang/Object";
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
