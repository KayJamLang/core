package com.github.kayjamlang.core.containers

import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.data.{Annotation, Argument}
import com.github.kayjamlang.core.opcodes.AccessType
import com.github.kayjamlang.core.{ArrayList, Type}

class NamedExpressionFunctionContainer(name: String, children: ArrayList[Expression], accessType: AccessType, line: Int) extends FunctionContainer(name, children, accessType, ArrayList(new Argument(Type.FUNCTION_REF, name)).asInstanceOf[ArrayList[Argument]], Type.VOID, new ArrayList[Annotation], line)