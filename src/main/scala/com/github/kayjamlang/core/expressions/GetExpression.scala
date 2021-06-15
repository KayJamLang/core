package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.opcodes.AccessType

class GetExpression(val root: Expression, val value: Expression, line: Int) extends Expression(AccessType NONE, line)