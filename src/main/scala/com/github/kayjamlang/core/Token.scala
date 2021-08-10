package com.github.kayjamlang.core

import java.util.regex.Pattern

class Token(val `type`: Token.Type, val value: String)
object Token {
  abstract class Type(regex: String, n: String, o: Int) extends Enum[Type](n, o) {
    val pattern: Pattern = Pattern compile s"^$regex"

    def endOfMatch(s: String): Int = {
      val m = pattern.matcher(s)
      if (m.find) return m.end
      -1
    }
  }

  object Type {
    val values: ArrayList[Type] = new ArrayList[Type]()

    case object COMMENT extends Type("//(.+)", "COMMENT", 0)
    values += COMMENT

    case object TK_NAMESPACE_DELIMITER extends Type("\\\\", "TK_NAMESPACE_DELIMITER", 1)
    values += TK_NAMESPACE_DELIMITER

    case object TK_REF extends Type("->", "TK_REF", 2)
    values += TK_REF

    case object TK_ANNOTATION extends Type("\\@", "TK_ANNOTATION", 3)
    values += TK_ANNOTATION

    case object TK_NEW_LINE extends Type("\\R", "TK_NEW_LINE", 4)
    values += TK_NEW_LINE

    case object TK_COMPANION_ACCESS extends Type("::", "TK_COMPANION_ACCESS", 5)
    values += TK_COMPANION_ACCESS

    case object TK_COLON extends Type(":", "TK_COLON", 6)
    values += TK_COLON

    case object TK_OPEN extends Type("\\(", "TK_OPEN", 7)
    values += TK_OPEN

    case object TK_CLOSE extends Type("\\)", "TK_CLOSE", 8)
    values += TK_CLOSE

    case object TK_SEMI extends Type(";", "TK_SEMI", 9)
    values += TK_SEMI

    case object TK_COMMA extends Type(",", "TK_COMMA", 10)
    values += TK_COMMA

    case object TK_NULLABLE extends Type("\\?", "TK_NULLABLE", 11)
    values += TK_NULLABLE

    case object TK_OPEN_SQUARE_BRACKET extends Type("\\[", "TK_OPEN_SQUARE_BRACKET", 12)
    values += TK_OPEN_SQUARE_BRACKET

    case object TK_CLOSE_SQUARE_BRACKET extends Type("\\]", "TK_CLOSE_SQUARE_BRACKET", 13)
    values += TK_CLOSE_SQUARE_BRACKET

    case object OPEN_BRACKET extends Type("\\{", "OPEN_BRACKET", 14)
    values += OPEN_BRACKET

    case object CLOSE_BRACKET extends Type("\\}", "CLOSE_BRACKET", 15)
    values += CLOSE_BRACKET

    case object DIFFERENT extends Type("<>", "DIFFERENT", 16)
    values += DIFFERENT

    case object BOOL extends Type("(true|false)", "BOOL", 17)
    values += BOOL

    case object NULL extends Type("null", "NULL", 18)
    values += NULL

    case object STRING extends Type("(\"[^\"]+\"|\"\")", "STRING", 19)
    values += STRING

    case object REAL extends Type("(\\d*)\\.\\d+", "REAL", 20)
    values += REAL

    case object LONG extends Type("\\d+[lL]", "LONG", 21)
    values += LONG

    case object INTEGER extends Type("\\d+", "INTEGER", 22)
    values += INTEGER

    case object IDENTIFIER extends Type("\\w+", "IDENTIFIER", 23)
    values += IDENTIFIER

    case object TK_PLUS_ONE extends Type("\\+\\+", "TK_PLUS_ONE", 24)
    values += TK_PLUS_ONE

    case object TK_MINUS_ONE extends Type("\\-\\-", "TK_MINUS_ONE", 25)
    values += TK_MINUS_ONE

    case object TK_MINUS extends Type("-", "TK_MINUS", 26)
    values += TK_MINUS

    case object TK_PLUS extends Type("\\+", "TK_PLUS", 27)
    values += TK_PLUS

    case object TK_MUL extends Type("\\*", "TK_MUL", 28)
    values += TK_MUL

    case object TK_DIV extends Type("/", "TK_DIV", 29)
    values += TK_DIV

    case object TK_AND extends Type("&&", "TK_AND", 30)
    values += TK_AND

    case object TK_OR extends Type("\\|\\|", "TK_OR", 31)
    values += TK_OR

    case object TK_EQUALS extends Type("==", "TK_EQUALS", 32)
    values += TK_EQUALS

    case object TK_NOT_EQUALS extends Type("!=", "TK_NOT_EQUALS", 33)
    values += TK_NOT_EQUALS

    case object TK_LESS_SIGN extends Type("<", "TK_LESS_SIGN", 34)
    values += TK_LESS_SIGN

    case object TK_LESS_EQUALS_SIGN extends Type("<=", "TK_LESS_EQUALS_SIGN", 35)
    values += TK_LESS_EQUALS_SIGN

    case object TK_GREATER_SIGN extends Type(">", "TK_GREATER_SIGN", 36)
    values += TK_GREATER_SIGN

    case object TK_GREATER_EQUALS_SIGN extends Type(">=", "TK_GREATER_EQUALS_SIGN", 37)
    values += TK_GREATER_EQUALS_SIGN

    case object TK_RANGE extends Type("\\.\\.", "TK_RANGE", 38)
    values += TK_RANGE

    case object TK_NOT extends Type("!", "TK_NOT", 39)
    values += TK_NOT

    case object TK_ASSIGN extends Type("=", "TK_ASSIGN", 40)
    values += TK_ASSIGN

    case object TK_ACCESS extends Type("\\.", "TK_ACCESS", 41)
    values += TK_ACCESS
  }
}
