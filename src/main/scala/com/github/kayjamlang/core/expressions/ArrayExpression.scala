package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.ArrayList
import com.github.kayjamlang.core.opcodes.AccessType

import scala.collection.mutable

class ArrayExpression(val values: ArrayList[Expression], line: Int) extends Expression(AccessType NONE, line)