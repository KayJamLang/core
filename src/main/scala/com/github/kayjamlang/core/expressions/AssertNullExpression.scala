package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.opcodes.AccessType

class AssertNullExpression(val expression: Expression, line: Int) extends Expression(AccessType NONE, line)