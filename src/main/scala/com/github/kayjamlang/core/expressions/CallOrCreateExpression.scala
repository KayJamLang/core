package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.ArrayList
import com.github.kayjamlang.core.opcodes.AccessType

import scala.collection.mutable

class CallOrCreateExpression(val name: String, val arguments: ArrayList[Expression], line: Int) extends Expression(AccessType NONE, line) {
  @Deprecated
  val functionName: String = null
}