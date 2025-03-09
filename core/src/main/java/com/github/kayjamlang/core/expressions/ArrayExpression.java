package com.github.kayjamlang.core.expressions;

import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.Arrays;
import java.util.List;

public class ArrayExpression extends Expression {
    /**
     * Array values
     */
    public final List<Expression> values;

    /**
     * @param values Array values
     * @param line   Start line
     */
    public ArrayExpression(List<Expression> values, int line) {
        super(AccessType.NONE, line);
        this.values = values;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        result.append(Arrays.toString(values.toArray()));
        result.append("]");
        return result.toString();
    }

    @Override
    public boolean isConstant() {
        for (Expression value : values) {
            if (!value.isConstant()) {
                return false;
            }
        }

        return true;
    }
}
