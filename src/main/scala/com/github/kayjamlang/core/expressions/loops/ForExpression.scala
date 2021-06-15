package com.github.kayjamlang.core.expressions.loops

import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.opcodes.AccessType

class ForExpression(val variableName: String, val range: Expression, val body: Expression, line: Int) extends Expression(AccessType PUBLIC, line)
