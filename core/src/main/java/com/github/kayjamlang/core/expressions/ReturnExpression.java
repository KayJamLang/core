package com.github.kayjamlang.core.expressions;

public class ReturnExpression extends Expression {

    public final Expression expression;

    public ReturnExpression(Expression expression, int line) {
        super(line);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Return{" +
                "expression=" + expression +
                ", line=" + line +
                '}';
    }

    @Override
    public boolean isConstant() {
        return expression.isConstant();
    }
}