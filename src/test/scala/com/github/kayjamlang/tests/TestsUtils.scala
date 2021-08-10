package com.github.kayjamlang.tests

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.exceptions.LexerException
import com.github.kayjamlang.core.exceptions.ParserException
import com.github.kayjamlang.core.expressions.Expression


object TestsUtils {
  @throws[ParserException]
  @throws[LexerException]
  def parse(code: String): Expression = getParser(code).readTopExpression

  def getParser(code: String) = new KayJamParser(new KayJamLexer(code))
}
