package com.github.kayjamlang.core.expressions.data;

import com.github.kayjamlang.core.Token;

/**
 * Operation types
 */
public enum Operation {

    PLUS(Token.Type.TK_PLUS),
    MINUS(Token.Type.TK_MINUS),
    DIVISION(Token.Type.TK_DIV),
    MULTIPLY(Token.Type.TK_MUL),

    EQUALS(Token.Type.TK_EQUALS),
    NOT_EQUALS(Token.Type.TK_NOT_EQUALS),
    OR(Token.Type.TK_OR),
    AND(Token.Type.TK_AND),

    LESS_SIGN(Token.Type.TK_LESS_SIGN),
    LESS_EQUALS_SIGN(Token.Type.TK_LESS_EQUALS_SIGN),
    GREATER_SIGN(Token.Type.TK_GREATER_SIGN),
    GREATER_EQUALS_SIGN(Token.Type.TK_GREATER_EQUALS_SIGN),

    BITWISE_OR(Token.Type.TK_BITWISE_OR),
    BITWISE_AND(Token.Type.TK_BITWISE_AND),
    BITWISE_SHIFT_LEFT(Token.Type.TK_BITWISE_SHIFT_LEFT),
    BITWISE_SHIFT_RIGHT(Token.Type.TK_BITWISE_SHIFT_RIGHT),

    RANGE(Token.Type.TK_RANGE);

    public final Token.Type tkType;

    Operation(Token.Type tkType) {
        this.tkType = tkType;
    }

    public static Operation get(Token.Type type){
        for(Operation op: values())
            if(op.tkType==type)
                return op;

        return null;
    }
}
