package com.github.kayjamlang.core.containers

import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.data.{Annotation, Argument}
import com.github.kayjamlang.core.opcodes.AccessType
import com.github.kayjamlang.core.{AdvancedMutableList, Type}

class NamedExpressionFunctionContainer(name: String, children: AdvancedMutableList[Expression], accessType: AccessType, line: Int) extends FunctionContainer(name, children, accessType, AdvancedMutableList(new Argument(Type.FUNCTION_REF, name)).asInstanceOf[AdvancedMutableList[Argument]], Type.VOID, new AdvancedMutableList[Annotation], line)