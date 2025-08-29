package com.github.kayjamlang.core.statements;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Annotation;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.List;

public class TypeAliasStatement {
    public final String aliasName;
    public final Type type;
    public final List<Annotation> annotations;
    public final AccessType accessType;
    public final int line;

    public TypeAliasStatement(
            String aliasName,
            Type type,
            List<Annotation> annotations,
            AccessType accessType,
            int line
    ) {
        this.aliasName = aliasName;
        this.type = type;
        this.annotations = annotations;
        this.accessType = accessType;
        this.line = line;
    }
}
