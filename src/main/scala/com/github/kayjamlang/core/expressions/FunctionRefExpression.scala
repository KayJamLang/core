package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.expressions.data.Argument
import com.github.kayjamlang.core.opcodes.AccessType
import com.github.kayjamlang.core.{AdvancedMutableList, Type}

class FunctionRefExpression(val arguments: AdvancedMutableList[Argument], val expression: Expression, val typeOfReturn: Type, line: Int) extends Expression(AccessType NONE, line)