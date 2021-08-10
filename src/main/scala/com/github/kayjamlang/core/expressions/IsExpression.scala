package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.Type
import com.github.kayjamlang.core.opcodes.AccessType

class IsExpression(val expression: Expression, val verifyType: Type, line: Int) extends Expression(AccessType NONE, line)