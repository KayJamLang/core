package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.ArrayList
import com.github.kayjamlang.core.opcodes.AccessType

import scala.collection.mutable

class UseExpression(line: Int, var required: ArrayList[String], val from: String) extends Expression(AccessType NONE, line) {
  def this(required: ArrayList[String], from: String, line: Int) {
    this(line, required, from)
  }
}
