//package com.github.kayjamlang.core.expressions.data
//
//case class Operation(`type`: Token.Type) extends Enumeration
//object Operation extends Enumeration {
//  case object PLUS extends Operation(Token.Type TK_PLUS)
//
//  case object MINUS extends Operation(Token.Type TK_MINUS)
//
//  case object DIVISION extends Operation(Token.Type TK_DIV)
//
//  case object MULTIPLY extends Operation(Token.Type TK_MUL)
//
//  case object EQUALS extends Operation(Token.Type TK_EQUALS)
//
//  case object NOT_EQUALS extends Operation(Token.Type TK_NOT_EQUALS)
//
//  case object OR extends Operation(Token.Type TK_OR)
//
//  case object AND extends Operation(Token.Type TK_AND)
//
//  case object LESS_SIGN extends Operation(Token.Type TK_LESS_SIGN)
//
//  case object LESS_EQUALS_SIGN extends Operation(Token.Type TK_LESS_EQUALS_SIGN)
//
//  case object GREATER_SIGN extends Operation(Token.Type TK_GREATER_SIGN)
//
//  case object GREATER_EQUALS_SIGN extends Operation(Token.Type TK_GREATER_EQUALS_SIGN)
//
//  case object RANGE extends Operation(Token.Type TK_RANGE)
//
//  def get(`type`: Token.Type): Operation = {
//    `type` match {
//      case Token.Type.TK_PLUS => PLUS
//      case Token.Type.TK_MINUS => MINUS
//      case Token.Type.TK_DIV => DIVISION
//      case Token.Type.TK_MUL => MULTIPLY
//      case Token.Type.TK_EQUALS => EQUALS
//      case Token.Type.TK_NOT_EQUALS => NOT_EQUALS
//      case Token.Type.TK_OR => OR
//      case Token.Type.TK_AND => AND
//      case Token.Type.TK_LESS_SIGN => LESS_SIGN
//      case Token.Type.TK_LESS_EQUALS_SIGN => LESS_EQUALS_SIGN
//      case Token.Type.TK_GREATER_SIGN => GREATER_SIGN
//      case Token.Type.TK_GREATER_EQUALS_SIGN => GREATER_EQUALS_SIGN
//      case Token.Type.TK_RANGE => RANGE
//      case _ => null
//    }
//  }
//}