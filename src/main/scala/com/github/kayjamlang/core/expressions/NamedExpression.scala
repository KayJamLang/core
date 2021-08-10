package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.{AdvancedMutableList, Type}
import com.github.kayjamlang.core.expressions.data.Argument

import java.util

class NamedExpression(val name: String, expression: Expression, line: Int) extends FunctionRefExpression(new AdvancedMutableList[Argument], expression, Type ANY, line)