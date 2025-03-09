package com.github.kayjamlang.backend.jvm.types;

import com.github.kayjamlang.core.expressions.data.Operation;
import org.objectweb.asm.MethodVisitor;

public abstract class JVMType {
    public abstract String getDescriptor();

    protected abstract JVMType operation(Operation operation, JVMType rightValue);

    protected abstract int operationBytecode(Operation operation);

    public JVMType getTypeAfterOperation(Operation operation, JVMType rightValue) {
        switch (operation) {
            case EQUALS:
            case NOT_EQUALS:
                return BooleanJVMType.INSTANCE;
            case PLUS:
                if(rightValue instanceof StringJVMType) {
                    return StringJVMType.INSTANCE;
                }
        }

        return operation(operation, rightValue);
    }

    public void visitOperation(MethodVisitor visitor, Operation operation, JVMType rightValue) {
        visitor.visitInsn(operationBytecode(operation));
    }
}
