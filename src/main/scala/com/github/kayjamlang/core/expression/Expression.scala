package com.github.kayjamlang.core.expression

import com.github.kayjamlang.core.opcodes.AccessType

import scala.collection.mutable

abstract class Expression (val access: AccessType, val line: Int) extends Cloneable {
  var data = new mutable.HashMap[String, Any]

  @SuppressWarnings(Array("unchecked"))
  @throws[CloneNotSupportedException]
  override protected def clone: Expression = {
    val expression = super.clone.asInstanceOf[Expression]
    expression.data = data clone()
    expression
  }
}