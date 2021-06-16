package com.github.kayjamlang.core

import scala.collection.mutable
import scala.util.control.Breaks.break
import scala.util.control.ControlThrowable

class KayJamLexer(value: String) {
  var input = new StringBuilder(value)
  var token: Token = _
  var line = 1
  var finished = false
  var errorMessage = ""
  var blankChars: mutable.Set[Character] = mutable.HashSet[Character](8.toChar, 9.toChar, 11.toChar,
    12.toChar, 32.toChar)

  blankChars add 8.toChar
  blankChars add 9.toChar
  blankChars add 11.toChar
  blankChars add 12.toChar
  blankChars add 32.toChar

  moveAhead()

  def moveAhead(needToken: Token.Type = null): Unit = {
    if (finished) return
    if (input isEmpty) {
      finished = true
      return
    }
    fixNewLine()
    ignoreWhiteSpaces()
    if (findNextToken(needToken)) {
      while ( {
        (token.`type` eq Token.Type.TK_NEW_LINE) || (token.`type` eq Token.Type.COMMENT)
      }) {
        line += 1
        if (input isEmpty) {
          finished = true
          return
        }

        ignoreWhiteSpaces()
        findNextToken(needToken)
      }
      return
    }
    finished = true
    if (input nonEmpty)
      errorMessage = s"Unexpected symbol: '${input.charAt(0)}'"
  }

  private def ignoreWhiteSpaces(): Unit = {
    var charsToDelete = 0
    try {
      while (blankChars contains (input charAt charsToDelete)) {
        charsToDelete += 1
        if (input.toString.length == charsToDelete)
          break
      }
    } catch { case _: ControlThrowable => }
    if (charsToDelete > 0)
      input delete(0, charsToDelete)
  }

  private def fixNewLine(): Unit = {
    input = new StringBuilder(input.toString replace('\r', ' '))
  }

  def getLine: Int = line

  private def findNextToken(needToken: Token.Type = null): Boolean = {
    if(needToken!=null&&tryToken(needToken))
      return true

    for (t <- Token.Type values) {
      if(tryToken(t))
        return true
    }

    false
  }

  private def tryToken(t: Token.Type): Boolean = {
    val end = t endOfMatch input.toString
    if (end != -1) {
      val lexema = input substring(0, end)
      token = new Token(t, lexema)
      input delete(0, end)
      return true
    }

    false
  }

  def currentToken: Token = token

  def isSuccessful: Boolean = errorMessage isEmpty

  def isFinished: Boolean = finished
}
