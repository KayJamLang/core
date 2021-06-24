package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.opcodes.AccessType

class ConstantValueExpression(val name: String, val value: ValueExpression, line: Int) extends Expression(AccessType NONE, line)