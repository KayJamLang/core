package com.github.kayjamlang.core.expressions.loops

import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.opcodes.AccessType

class WhileExpression(val condition: Expression, val expression: Expression, line: Int) extends Expression(AccessType NONE, line)