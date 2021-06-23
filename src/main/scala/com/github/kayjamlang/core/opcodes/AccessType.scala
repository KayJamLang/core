package com.github.kayjamlang.core.opcodes

trait AccessType
object AccessType extends Enumeration {
  case object NONE      extends AccessType
  case object PUBLIC    extends AccessType
  case object PRIVATE   extends AccessType
  case object COMPANION extends AccessType
}