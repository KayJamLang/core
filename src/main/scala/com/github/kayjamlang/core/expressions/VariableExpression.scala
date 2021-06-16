package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.Type
import com.github.kayjamlang.core.opcodes.AccessType

class VariableExpression(val name: String,
                         val expression: Expression,
                         val variableType: Type = null,
                         accessType: AccessType,
                         line: Int) extends Expression(accessType, line)