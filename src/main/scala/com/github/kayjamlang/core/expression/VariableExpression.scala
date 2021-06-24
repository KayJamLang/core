package com.github.kayjamlang.core.expression

import com.github.kayjamlang.core.opcodes.AccessType

class VariableExpression(var value: Any, access: AccessType, line: Int) extends Expression(access, line)