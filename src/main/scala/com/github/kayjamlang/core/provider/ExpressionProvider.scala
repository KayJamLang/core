package com.github.kayjamlang.core.provider

import com.github.kayjamlang.core.Type
import com.github.kayjamlang.core.exceptions.TypeException

class ExpressionProvider[A, B, C, D] {
  def provide(mainProvider: MainExpressionProvider[B, C, D], context: C, argsContext: C, expression: A): B = mainProvider.defaultObject

  @throws[TypeException]
  def getType(mainProvider: MainExpressionProvider[B, C, D], context: C, argsContext: C, expression: A): Type = Type ANY
}
