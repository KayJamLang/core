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

  moveAhead()

  def moveAhead(needToken: Token.Type = null): Unit = {
    if (finished) return
    if (input isEmpty) {
      finished = true
      return
    }

    ignoreWhiteSpaces()
    if (findNextToken(needToken)) {
      while ((token.`type` eq Token.Type.TK_NEW_LINE) || (token.`type` eq Token.Type.COMMENT)) {
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

  def ignoreWhiteSpaces(): Unit = {
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

  def getLine: Int = line

  def findNextToken(needToken: Token.Type = null): Boolean = {
    if(needToken!=null&&tryToken(needToken))
      return true

    for (t <- Token.Type values) {
      if(tryToken(t))
        return true
    }

    false
  }

  def _tryToken(t: Token.Type): Boolean = { // TODO: PLEASE RENAME!
    val response = t matchStr input.toString
    if (response != null) {
      token = new Token(t, response.group(response.groupCount))
      return true
    }

    false
  }

  def tryToken(t: Token.Type): Boolean = {
    val response = t matchStr input.toString
    if (response != null) {
      token = new Token(t, response.group(response.groupCount))
      input delete(0, response.end)
      return true
    }

    false
  }

  def currentToken: Token = token

  def isSuccessful: Boolean = errorMessage isEmpty

  def isFinished: Boolean = finished
}
