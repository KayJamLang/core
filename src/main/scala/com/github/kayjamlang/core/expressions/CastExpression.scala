package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.Type
import com.github.kayjamlang.core.opcodes.AccessType

class CastExpression(val expression: Expression, val castType: Type, line: Int) extends Expression(AccessType NONE, line)