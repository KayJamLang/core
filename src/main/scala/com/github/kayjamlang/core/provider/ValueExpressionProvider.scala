package com.github.kayjamlang.core.provider

import com.github.kayjamlang.core.Type
import com.github.kayjamlang.core.exceptions.TypeException
import com.github.kayjamlang.core.expressions.ValueExpression
import com.github.kayjamlang.core.opcodes.AccessType

class ValueExpressionProvider[A, B, C](accessType: AccessType) extends ExpressionProvider[ValueExpression, A, B, C](accessType) {
  @throws[TypeException]
  override def getType(mainProvider: MainExpressionProvider[A, B, C], context: B, argsContext: B, expression: ValueExpression): Type = {
    val value = expression.value
    if (value == null) {
      val any = Type.NULL clone()
      any.nullable = true
      return any
    }

    value match {
      case _: Double  => Type DOUBLE
      case _: Int     => Type INTEGER
      case _: Long    => Type LONG
      case _: String  => Type STRING
      case _: Boolean => Type BOOLEAN
      case _          => Type ANY
    }
  }
}
