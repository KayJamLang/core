package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.opcodes.AccessType

class VariableExpression(val name: String,
                         val expression: Expression, accessType: AccessType, line: Int) extends Expression(accessType, line)