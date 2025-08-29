package com.github.kayjamlang.backend.jvm.types;

import com.github.kayjamlang.backend.jvm.types.data.ConstInfo;
import com.github.kayjamlang.backend.jvm.types.data.FunctionInfo;
import com.github.kayjamlang.backend.jvm.types.data.PropertyInfo;
import com.github.kayjamlang.core.expressions.data.Operation;
import org.objectweb.asm.MethodVisitor;

import java.util.HashMap;
import java.util.Map;

public abstract class JVMType {
    public final Map<String, ConstInfo> constants = new HashMap<>();
    public final Map<String, PropertyInfo> properties = new HashMap<>();
    public final Map<String, FunctionInfo> functions = new HashMap<>();

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
