package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.Stmt;
import com.github.kayjamlang.core.opcodes.AccessType;

public abstract class Expression extends Stmt implements Cloneable {
    /**
     * Type of access
     */
    @Deprecated
    public final AccessType accessType;

    /**
     * Creates Expression
     * @param accessType Type of access
     * @param line Expression line
     */
    protected Expression(AccessType accessType, int line) {
        super(line);
        this.accessType = accessType;
    }

    /**
     * Creates Expression
     * @param line Expression line
     */
    protected Expression(int line) {
        super(line);
        this.accessType = AccessType.NONE;
    }
}