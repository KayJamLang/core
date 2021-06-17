package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.ArrayList
import com.github.kayjamlang.core.opcodes.AccessType

import scala.collection.mutable

class UseExpression(line: Int, var required: ArrayList[String], val from: String, val expression: Expression) extends Expression(AccessType NONE, line) {
  def this(required: ArrayList[String], from: String, line: Int) {
    this(line, required, from, null)
  }

  /**
   * @deprecated please don't use this
   */
  @deprecated
  def this(expression: Expression, line: Int) {
    this(line, null, null, expression)
  }

  /**
   * @deprecated please don't use this
   */
  @deprecated
  trait UseInterface {
    @throws[Exception]
    def getExpression(path: String): Expression
  }
}
