package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.List;

public class UseExpression extends Expression {
    public List<String> required;
    public final String from;

    public UseExpression(List<String> required, String from, int line){
        super(AccessType.NONE, line);
        this.required = required;
        this.from = from;

        expression = null;
    }

    /**
     * @deprecated please don't use this
     */
    @Deprecated
    public final Expression expression;

    /**
     * @deprecated please don't use this
     */
    @Deprecated
    public UseExpression(Expression expression, int line) {
        super(AccessType.NONE, line);
        this.expression = expression;
        this.from = null;
    }

    /**
     * @deprecated please don't use this
     */
    @Deprecated
    public interface UseInterface{
        Expression getExpression(String path) throws Exception;
    }
}