package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.opcodes.AccessType

class AccessExpression(val root: Expression, val child: Expression, line: Int) extends Expression(AccessType NONE, line)