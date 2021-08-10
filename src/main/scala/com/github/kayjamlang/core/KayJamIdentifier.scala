//package com.github.kayjamlang.core
//
//import java.util.regex.Pattern
//
//case class KayJamIdentifier(regex: String) extends Enumeration {
//  val pattern: Pattern = Pattern compile("^" + regex + "$")
//
//  def endOfMatch(s: String): Boolean = pattern matcher s find
//}
//
//object KayJamIdentifier extends Enumeration {
//  case object VAR         extends KayJamIdentifier("var")
//  case object FUNCTION    extends KayJamIdentifier("function|fun")
//  case object NAMED       extends KayJamIdentifier("named")
//  case object PUBLIC      extends KayJamIdentifier("public")
//  case object PRIVATE     extends KayJamIdentifier("private")
//  case object WHILE       extends KayJamIdentifier("while")
//  case object FOR         extends KayJamIdentifier("for")
//  case object CLASS       extends KayJamIdentifier("class")
//  case object OBJECT      extends KayJamIdentifier("object")
//  case object RETURN      extends KayJamIdentifier("return")
//  case object CONSTRUCTOR extends KayJamIdentifier("constructor")
//  case object COMPANION   extends KayJamIdentifier("companion")
//  case object NAMESPACE   extends KayJamIdentifier("namespace")
//  case object USE         extends KayJamIdentifier("use")
//  case object CAST        extends KayJamIdentifier("case")
//  case object IS          extends KayJamIdentifier("is")
//  case object IF          extends KayJamIdentifier("if")
//  case object IN          extends KayJamIdentifier("in")
//  case object ELSE        extends KayJamIdentifier("else")
//  case object PACK        extends KayJamIdentifier("pack")
//  case object CONSTANT    extends KayJamIdentifier("constant")
//  case object FROM        extends KayJamIdentifier("from")
//
//  def find(str: String): KayJamIdentifier = {
//    if (VAR endOfMatch str)
//      return VAR
//    if (FUNCTION endOfMatch str)
//      return FUNCTION
//    if (NAMED endOfMatch str)
//        return NAMED
//    if (PUBLIC endOfMatch(str))
//      return PUBLIC
//    if (PRIVATE endOfMatch str)
//      return PRIVATE
//    if (WHILE endOfMatch str)
//      return WHILE
//    if (FOR endOfMatch str)
//      return FOR
//    if (CLASS endOfMatch str)
//      return CLASS
//    if (OBJECT endOfMatch str)
//      return OBJECT
//    if (RETURN endOfMatch str)
//      return RETURN
//    if (CONSTRUCTOR endOfMatch str)
//      return CONSTRUCTOR
//    if (COMPANION endOfMatch str)
//      return COMPANION
//    if (NAMESPACE endOfMatch str)
//      return NAMESPACE
//    if (USE endOfMatch str)
//      return USE
//    if (CAST endOfMatch str)
//      return CAST
//    if (IS endOfMatch str)
//      return IS
//    if (IN endOfMatch str)
//      return IN
//    if (ELSE endOfMatch str)
//      return ELSE
//    if (PACK endOfMatch str)
//      return PACK
//    if (CONSTANT endOfMatch str)
//      return CONSTANT
//    if (FROM endOfMatch str)
//      return FROM
//    null
//  }
//}