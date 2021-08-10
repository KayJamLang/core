package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.opcodes.AccessType

class VariableLinkExpression(val name: String, line: Int) extends Expression(AccessType NONE, line)