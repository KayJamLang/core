package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.expressions.data.Operation
import com.github.kayjamlang.core.opcodes.AccessType

class OperationExpression(val left: Expression, val right: Expression, val operation: Operation, line: Int) extends Expression(AccessType NONE, line)