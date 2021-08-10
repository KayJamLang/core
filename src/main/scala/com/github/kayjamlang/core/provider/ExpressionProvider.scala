package com.github.kayjamlang.core.provider

import com.github.kayjamlang.core.Type
import com.github.kayjamlang.core.exception.TypeException
import com.github.kayjamlang.core.expression.Expression
import com.github.kayjamlang.core.opcodes.AccessType

class ExpressionProvider[A, B, C, D](accessType: AccessType) extends Expression(accessType, -1) {
  def provide(mainProvider: MainExpressionProvider[B, C, D], context: C, argsContext: C, expression: A): B = mainProvider.defaultObject

  @throws[TypeException]
  def getType(mainProvider: MainExpressionProvider[B, C, D], context: C, argsContext: C, expression: A): Type = Type ANY
}
