package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.Type
import com.github.kayjamlang.core.opcodes.AccessType

class ValueExpression(val value: Any, val valueType: Type) extends Expression(AccessType NONE, -1) {
  override def toString: String = value toString
}
