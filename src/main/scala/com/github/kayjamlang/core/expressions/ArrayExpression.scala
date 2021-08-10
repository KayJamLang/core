package com.github.kayjamlang.core.expressions

import com.github.kayjamlang.core.opcodes.AccessType

import scala.collection.mutable

class ArrayExpression(val values: mutable.MutableList[Expression], line: Int) extends Expression(AccessType NONE, line)