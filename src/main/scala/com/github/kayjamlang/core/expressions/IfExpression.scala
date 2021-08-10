package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.opcodes.AccessType

class IfExpression(val condition: Expression, val ifTrue: Expression, val ifFalse: Expression, line: Int) extends Expression(AccessType NONE, line)
