package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;
import com.github.kayjamlang.core.statements.Statement;

import java.util.HashMap;
import java.util.Map;

public abstract class Expression extends Statement {

    /**
     * Specific data for provider
     */
    public Map<String, Object> data = new HashMap<>();

    /**
     * Creates Expression
     * @param line Expression line
     */
    protected Expression(int line) {
        super(line);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Expression clone() throws CloneNotSupportedException {
        Expression expression = (Expression) super.clone();
        expression.data = (Map<String, Object>) ((HashMap<String, Object>) data).clone();
        return expression;
    }

    public abstract boolean isConstant();
}