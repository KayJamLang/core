package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.opcodes.AccessType

class ReturnExpression(val expression: Expression, line: Int) extends Expression(AccessType NONE, line) {
  override def toString: String = s"Return{expression=$expression, identifier=$accessType, line=$line}"
}