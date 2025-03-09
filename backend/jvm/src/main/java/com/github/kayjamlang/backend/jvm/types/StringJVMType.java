package com.github.kayjamlang.backend.jvm.types;

import com.github.kayjamlang.core.expressions.data.Operation;

import java.util.Objects;

public class StringJVMType extends JVMType {
    public static final StringJVMType INSTANCE = new StringJVMType();

    @Override
    public String getDescriptor() {
        return "Ljava/lang/String";
    }

    @Override
    protected JVMType operation(Operation operation, JVMType rightValue) {
        if (Objects.requireNonNull(operation) == Operation.PLUS) {
            return this;
        }

        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    protected int operationBytecode(Operation operation) {
        throw new UnsupportedOperationException("Operation not supported");
    }
}
