package com.github.kayjam.core;

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
    GREATER_EQUALS_SIGN(Token.Type.TK_GREATER_EQUALS_SIGN);

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
