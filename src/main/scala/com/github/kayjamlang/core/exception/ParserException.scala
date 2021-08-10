package com.github.kayjamlang.core.exception

import com.github.kayjamlang.core.KayJamLexer

class ParserException(message: String) extends Exception(message) {
  def this(lexer: KayJamLexer, message: String) {
    this(s"$message in ${lexer.getLine} line")
  }

  def this(line: Int, message: String) {
    this(s"$message + in + $line + line")
  }
}