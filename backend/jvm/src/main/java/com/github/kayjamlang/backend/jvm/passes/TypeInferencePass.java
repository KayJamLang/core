package com.github.kayjamlang.backend.jvm.passes;

import com.github.kayjamlang.backend.jvm.SymbolRegistry;
import com.github.kayjamlang.backend.jvm.TypeKayJamStatementVisitor;
import com.github.kayjamlang.backend.jvm.types.ClassJVMType;
import com.github.kayjamlang.backend.jvm.types.data.PropertyInfo;

public class TypeInferencePass {
    private final SymbolRegistry symbolRegistry;

    public TypeInferencePass(SymbolRegistry symbolRegistry) {
        this.symbolRegistry = symbolRegistry;
    }

    public void inferTypes() {
        for (ClassJVMType classInfo : symbolRegistry.classes.values()) {
            TypeKayJamStatementVisitor typeVisitor = new TypeKayJamStatementVisitor(symbolRegistry, classInfo);
            for (PropertyInfo propInfo : classInfo.properties.values()) {
                if (propInfo.type == null
                        && propInfo.sourceProperty != null
                        && propInfo.sourceProperty.value != null
                ) {
                    propInfo.type = typeVisitor.visit(propInfo.sourceProperty.value);
                    if (propInfo.type == null) {
                        throw new IllegalStateException("Could not infer type for property " + classInfo.name + "." + propInfo.name);
                    }
                }
            }

//            for (ConstInfo constInfo : classInfo.constants.values()) {
//                if (constInfo.type == null && constInfo.sourceConstant != null && constInfo.sourceConstant.value != null) {
//                    constInfo.type = typeVisitor.visit(constInfo.sourceConstant.value);
//                    if (constInfo.type == null) {
//                        System.err.println("Warning: Could not infer type for constant " + classInfo.name + "." + constInfo.name + ". Defaulting to 'any'.");
//                        constInfo.type = symbolRegistry.resolveType(com.github.kayjamlang.core.Type.ANY);
//                    }
//                } else if (constInfo.type == null && constInfo.sourceConstant != null && constInfo.sourceConstant.value == null) {
//                    System.err.println("Error: Constant " + classInfo.name + "." + constInfo.name + " has no explicit type and no initial value. This is likely an error.");
//                    constInfo.type = symbolRegistry.resolveType(com.github.kayjamlang.core.Type.ANY);
//                }
//            }
        }
    }
}