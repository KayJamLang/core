package com.github.kayjamlang.core

import scala.collection.mutable
import scala.util.control.Breaks.break
import scala.util.control.ControlThrowable

class KayJamLexer(value: String) {
  var input = new StringBuilder(value)
  var token: Token = null
  var line = 1
  var finished = false
  var errorMessage = ""
  var blankChars: mutable.Set[Character] = new mutable.HashSet[Character]

  blankChars add 8.toChar
  blankChars add 9.toChar
  blankChars add 11.toChar
  blankChars add 12.toChar
  blankChars add 32.toChar

  moveAhead()

  def moveAhead(): Unit = {
    if (finished) return
    if (input.isEmpty) {
      finished = true
      return
    }
    fixNewLine()
    ignoreWhiteSpaces()
    if (findNextToken) {
      while ( {
        (token.`type` eq Token.Type.TK_NEW_LINE) || (token.`type` eq Token.Type.COMMENT)
      }) {
        line += 1
        if (input isEmpty) {
          finished = true
          return
        }
        ignoreWhiteSpaces()
        findNextToken
      }
      return
    }
    finished = true
    if (input nonEmpty) errorMessage = "Unexpected symbol: '" + input.charAt(0) + "'"
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


  private def findNextToken: Boolean = {
    for (t <- Token.Type values) {
      val end = t.endOfMatch(input toString)
      if (end != -1) {
        val lexema = input substring(0, end)
        token = new Token(t, lexema)
        input delete(0, end)
        return true
      }
    }
    false
  }

  def currentToken: Token = token

  def isSuccessful: Boolean = errorMessage isEmpty

  def isFinished: Boolean = finished
}
