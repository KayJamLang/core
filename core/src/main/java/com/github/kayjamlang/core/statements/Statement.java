package com.github.kayjamlang.core.statements;

public abstract class Statement implements Cloneable {
    /**
     * Expression line
     */
    public final int line;

    protected Statement(int line) {
        this.line = line;
    }
}
