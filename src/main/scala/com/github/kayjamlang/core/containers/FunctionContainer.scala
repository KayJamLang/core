package com.github.kayjamlang.core.containers

import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.data.{Annotation, Argument}
import com.github.kayjamlang.core.opcodes.AccessType
import com.github.kayjamlang.core.{AdvancedMutableList, Type}

class FunctionContainer(val name: String, children: AdvancedMutableList[Expression], accessType: AccessType, val arguments: AdvancedMutableList[Argument], val returnType: Type, val annotations: AdvancedMutableList[Annotation], line: Int) extends Container(children, accessType, line)