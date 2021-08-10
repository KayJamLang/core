package com.github.kayjamlang.core.containers

import com.github.kayjamlang.core.ArrayList
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.data.Argument
import com.github.kayjamlang.core.opcodes.AccessType

class ConstructorContainer(val arguments: ArrayList[Argument], children: ArrayList[Expression], accessType: AccessType, line: Int) extends Container(children, accessType, line)