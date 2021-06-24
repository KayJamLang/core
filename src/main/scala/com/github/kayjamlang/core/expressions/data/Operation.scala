package com.github.kayjamlang.core.expressions.data

import com.github.kayjamlang.core.{ArrayList, Token}

abstract class Operation(val `type`: Token.Type, name: String, ordinal: Int) extends Enum[Operation](name, ordinal)
object Operation {
  val values = new ArrayList[Operation]()

  case object PLUS extends Operation(Token.Type TK_PLUS, "PLUS", 0)
  values += PLUS

  case object MINUS extends Operation(Token.Type TK_MINUS, "MINUS", 1)
  values += MINUS

  case object DIVISION extends Operation(Token.Type TK_DIV, "DIVISION", 2)
  values += DIVISION

  case object MULTIPLY extends Operation(Token.Type TK_MUL, "MULTIPLY", 3)
  values += MULTIPLY

  case object EQUALS extends Operation(Token.Type TK_EQUALS, "EQUALS", 4)
  values += EQUALS

  case object NOT_EQUALS extends Operation(Token.Type TK_NOT_EQUALS, "NOT_EQUALS", 5)
  values += NOT_EQUALS

  case object OR extends Operation(Token.Type TK_OR, "OR", 6)
  values += OR

  case object AND extends Operation(Token.Type TK_AND, "AND", 7)
  values += AND

  case object LESS_SIGN extends Operation(Token.Type TK_LESS_SIGN, "LESS_SIGN", 8)
  values += LESS_SIGN

  case object LESS_EQUALS_SIGN extends Operation(Token.Type TK_LESS_EQUALS_SIGN, "LESS_EQUALS_SIGN", 9)
  values += LESS_EQUALS_SIGN

  case object GREATER_SIGN extends Operation(Token.Type TK_GREATER_SIGN, "GREATER_SIGN", 10)
  values += GREATER_SIGN

  case object GREATER_EQUALS_SIGN extends Operation(Token.Type TK_GREATER_EQUALS_SIGN, "GREATER_EQUALS_SIGN", 11)
  values += GREATER_EQUALS_SIGN

  case object RANGE extends Operation(Token.Type TK_RANGE, "RANGE", 12)
  values += RANGE

  def get(`type`: Token.Type): Operation = {
    `type` match {
      case Token.Type.TK_PLUS => PLUS
      case Token.Type.TK_MINUS => MINUS
      case Token.Type.TK_DIV => DIVISION
      case Token.Type.TK_MUL => MULTIPLY
      case Token.Type.TK_EQUALS => EQUALS
      case Token.Type.TK_NOT_EQUALS => NOT_EQUALS
      case Token.Type.TK_OR => OR
      case Token.Type.TK_AND => AND
      case Token.Type.TK_LESS_SIGN => LESS_SIGN
      case Token.Type.TK_LESS_EQUALS_SIGN => LESS_EQUALS_SIGN
      case Token.Type.TK_GREATER_SIGN => GREATER_SIGN
      case Token.Type.TK_GREATER_EQUALS_SIGN => GREATER_EQUALS_SIGN
      case Token.Type.TK_RANGE => RANGE
      case _ => null
    }
  }
}
