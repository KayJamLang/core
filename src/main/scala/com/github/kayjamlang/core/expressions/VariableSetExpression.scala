package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.opcodes.AccessType

class VariableSetExpression(name: String, expression: Expression, line: Int) extends VariableExpression(name, expression, AccessType.NONE, line)