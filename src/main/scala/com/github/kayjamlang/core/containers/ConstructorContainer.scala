package com.github.kayjamlang.core.containers

import com.github.kayjamlang.core.AdvancedMutableList
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.data.Argument
import com.github.kayjamlang.core.opcodes.AccessType

class ConstructorContainer(val arguments: AdvancedMutableList[Argument], children: AdvancedMutableList[Expression], accessType: AccessType, line: Int) extends Container(children, accessType, line)