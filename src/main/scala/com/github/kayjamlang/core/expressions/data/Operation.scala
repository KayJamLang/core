package com.github.kayjamlang.core.expressions.data

import com.github.kayjamlang.core.Token

abstract class Operation(val `type`: Token.Type, name: String, ordinal: Int) extends Enum[Operation](name, ordinal)
object Operation {
    case object PLUS                extends Operation(Token.Type TK_PLUS,             "   PLUS",                  0)
    case object MINUS               extends Operation(Token.Type TK_MINUS,                "MINUS",                1)
    case object DIVISION            extends Operation(Token.Type TK_DIV,                  "DIVISION",             2)
    case object MULTIPLY            extends Operation(Token.Type TK_MUL,                  "MULTIPLY",             3)
    case object EQUALS              extends Operation(Token.Type TK_EQUALS,               "EQUALS",               4)
    case object NOT_EQUALS          extends Operation(Token.Type TK_NOT_EQUALS,           "NOT_EQUALS",           5)
    case object OR                  extends Operation(Token.Type TK_OR,                   "OR",                   6)
    case object AND                 extends Operation(Token.Type TK_AND,                  "AND",                  7)
    case object LESS_SIGN           extends Operation(Token.Type TK_LESS_SIGN,            "LESS_SIGN",            8)
    case object LESS_EQUALS_SIGN    extends Operation(Token.Type TK_LESS_EQUALS_SIGN,     "LESS_EQUALS_SIGN",     9)
    case object GREATER_SIGN        extends Operation(Token.Type TK_GREATER_SIGN,         "GREATER_SIGN",         10)
    case object GREATER_EQUALS_SIGN extends Operation(Token.Type TK_GREATER_EQUALS_SIGN,  "GREATER_EQUALS_SIGN",  11)
    case object RANGE               extends Operation(Token.Type TK_RANGE,                "RANGE",                12)

    def get(`type`: Token.Type): Operation = {
      `type` match {
        case Token.Type.TK_PLUS                 => PLUS
        case Token.Type.TK_MINUS                => MINUS
        case Token.Type.TK_DIV                  => DIVISION
        case Token.Type.TK_MUL                  => MULTIPLY
        case Token.Type.TK_EQUALS               => EQUALS
        case Token.Type.TK_NOT_EQUALS           => NOT_EQUALS
        case Token.Type.TK_OR                   => OR
        case Token.Type.TK_AND                  => AND
        case Token.Type.TK_LESS_SIGN            => LESS_SIGN
        case Token.Type.TK_LESS_EQUALS_SIGN     => LESS_EQUALS_SIGN
        case Token.Type.TK_GREATER_SIGN         => GREATER_SIGN
        case Token.Type.TK_GREATER_EQUALS_SIGN  => GREATER_EQUALS_SIGN
        case Token.Type.TK_RANGE                => RANGE
        case _                                  => null
      }
    }
}