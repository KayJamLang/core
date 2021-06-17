package com.github.kayjamlang.core

import java.util.regex.Pattern

abstract class KayJamIdentifier(regex: String, name: String, ordinal: Int) extends Enum[KayJamIdentifier](name, ordinal) {
  val pattern: Pattern = Pattern compile("^" + regex + "$")

  def endOfMatch(s: String): Boolean = pattern matcher s find
}

object KayJamIdentifier {
  val values = new ArrayList[KayJamIdentifier]()

  case object VAR extends KayJamIdentifier("var", "VAR", 0)
  values += VAR

  case object FUNCTION extends KayJamIdentifier("function|fun", "FUNCTION", 1)
  values += FUNCTION

  case object NAMED extends KayJamIdentifier("named", "NAMED", 2)
  values += NAMED

  case object PUBLIC extends KayJamIdentifier("public", "PUBLIC", 3)
  values += PUBLIC

  case object PRIVATE extends KayJamIdentifier("private", "PRIVATE", 4)
  values += PRIVATE

  case object WHILE extends KayJamIdentifier("while", "WHILE", 5)
  values += WHILE

  case object FOR extends KayJamIdentifier("for", "FOR", 6)
  values += FOR

  case object CLASS extends KayJamIdentifier("class", "CLASS", 7)
  values += CLASS

  case object OBJECT extends KayJamIdentifier("object", "OBJECT", 8)
  values += OBJECT

  case object RETURN extends KayJamIdentifier("return", "RETURN", 9)
  values += RETURN

  case object CONSTRUCTOR extends KayJamIdentifier("constructor", "CONSTRUCTOR", 10)
  values += CONSTRUCTOR

  case object COMPANION extends KayJamIdentifier("companion", "COMPANION", 11)
  values += COMPANION

  case object NAMESPACE extends KayJamIdentifier("namespace", "NAMESPACE", 12)
  values += NAMESPACE

  case object USE extends KayJamIdentifier("use", "USE", 13)
  values += USE

  case object CAST extends KayJamIdentifier("as", "AS", 14)
  values += CAST

  case object IS extends KayJamIdentifier("is", "IS", 15)
  values += IS

  case object IF extends KayJamIdentifier("if", "IF", 16)
  values += IF

  case object IN extends KayJamIdentifier("in", "IN", 17)
  values += IN

  case object ELSE extends KayJamIdentifier("else", "ELSE", 18)
  values += ELSE

  case object PACK extends KayJamIdentifier("pack", "PACK", 19)
  values += PACK

  case object CONSTANT extends KayJamIdentifier("const", "CONST", 20)
  values += CONSTANT

  case object FROM extends KayJamIdentifier("from", "FROM", 21)
  values += FROM

  def find(str: String): KayJamIdentifier = {
    for(t <- KayJamIdentifier.values)
      if(t endOfMatch str)
        return t
    null
  }
}