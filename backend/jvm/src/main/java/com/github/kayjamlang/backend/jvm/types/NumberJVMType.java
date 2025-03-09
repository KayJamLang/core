package com.github.kayjamlang.backend.jvm.types;

import com.github.kayjamlang.core.expressions.data.Operation;
import org.objectweb.asm.MethodVisitor;

public abstract class NumberJVMType extends JVMType {
    protected abstract int getPriority();

    @Override
    public JVMType operation(Operation operation, JVMType rightValue) {
        int rightPriority = getPriority();
        JVMType priorityType = rightPriority > getPriority() ? rightValue: this;
        switch (operation) {
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case DIVISION:
            case BITWISE_OR:
            case BITWISE_AND:
                return priorityType;

            case LESS_SIGN:
            case LESS_EQUALS_SIGN:
            case GREATER_SIGN:
            case GREATER_EQUALS_SIGN:
                return BooleanJVMType.INSTANCE;

            case BITWISE_SHIFT_LEFT:
            case BITWISE_SHIFT_RIGHT:
                return this;
        }

        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public void visitOperation(MethodVisitor visitor, Operation operation, JVMType rightValue) {
        int rightPriority = getPriority();
        if(rightPriority > getPriority()){
            visitor.visitInsn(rightValue.operationBytecode(operation));
        }else visitor.visitInsn(operationBytecode(operation));
    }
}
