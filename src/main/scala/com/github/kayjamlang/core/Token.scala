/*
package com.github.kayjamlang.core

import java.util
import java.util.regex.Pattern

class Token(val `type`: Type, val value: String) {
  case class Type(regex: String) extends Enumeration {
    val pattern: Pattern = Pattern compile s"^$regex"

    def endOfMatch(s: String): Int = {
      val m = pattern.matcher(s)
      if (m.find) return m.end
      -1
    }
  }

  object Type {
      val values: util.ArrayList[Token] = new util.ArrayList[Token]()

      case object COMMENT extends Type("//(.+)")

      case object TK_NAMESPACE_DELIMITER extends Type("\\\\")

      case object TK_REF extends Type("->")

      case object TK_ANNOTATION extends Type("\\@")

      case object TK_NEW_LINE extends Type("\\R")

      case object TK_COMPANION_ACCESS extends Type("::")

      case object TK_COLON extends Type(":")

      case object TK_OPEN extends Type("\\(")

      case object TK_CLOSE extends Type("\\)")

      case object TK_SEMI extends Type(";")

      case object TK_COMMA extends Type(",")

      case object TK_NULLABLE extends Type("\\?")

      case object TK_OPEN_SQUARE_BRACKET extends Type("\\[")

      case object TK_CLOSE_SQUARE_BRACKET extends Type("\\]")

      case object OPEN_BRACKET extends Type("\\{")

      case object CLOSE_BRACKET extends Type("\\}")

      case object DIFFERENT extends Type("<>")

      case object BOOL extends Type("(true|false)")

      case object NULL extends Type("null")

      case object STRING extends Type("(\"[^\"]+\"|\"\")")

      case object REAL extends Type("(\\d*)\\.\\d+")

      case object LONG extends Type("\\d+[lL]")

      case object INTEGER extends Type("\\d+")

      case object IDENTIFIER extends Type("\\w+")

      case object TK_PLUS_ONE extends Type("\\+\\+")

      case object TK_MINUS_ONE extends Type("\\-\\-")

      case object TK_MINUS extends Type("-")

      case object TK_PLUS extends Type("\\+")

      case object TK_MUL extends Type("\\*")

      case object TK_DIV extends Type("/")

      case object TK_AND extends Type("&&")

      case object TK_OR extends Type("\\|\\|")

      case object TK_EQUALS extends Type("==")

      case object TK_NOT_EQUALS extends Type("!=")

      case object TK_LESS_SIGN extends Type("<")

      case object TK_LESS_EQUALS_SIGN extends Type("<=")

      case object TK_GREATER_SIGN extends Type(">")

      case object TK_GREATER_EQUALS_SIGN extends Type(">=")

      case object TK_RANGE extends Type("\\.\\.")

      case object TK_NOT extends Type("!")

      case object TK_ASSIGN extends Type("=")

      case object TK_ACCESS extends Type("\\.")
  }
}*/
