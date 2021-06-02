package com.github.kayjamlang.core;

import java.util.HashMap;
import java.util.Map;

public abstract class Stmt implements Cloneable {
    /**
     * Specific data for provider
     */
    public Map<String, Object> data = new HashMap<>();

    /**
     * Expression line
     */
    public final int line;

    /**
     * Creates Stmt
     * @param line Expression line
     */
    protected Stmt(int line) {
        this.line = line;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Stmt clone() throws CloneNotSupportedException {
        Stmt stmt = (Stmt) super.clone();
        stmt.data = (Map<String, Object>) ((HashMap<String, Object>) data).clone();
        return stmt;
    }
}
