package com.github.kayjamlang.core.containers

import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.data.{Annotation, Argument}
import com.github.kayjamlang.core.opcodes.AccessType
import com.github.kayjamlang.core.{ArrayList, Type}

@deprecated("Use stmt version")
class FunctionContainer(val name: String, children: ArrayList[Expression], accessType: AccessType, val arguments: ArrayList[Argument], val returnType: Type, val annotations: ArrayList[Annotation], line: Int) extends Container(children, accessType, line) {
    val desc: String = name+"("+{
        var args = ""
        arguments.foreach {
            argument: Argument => args += argument.`type`.name
        }

        args
    }+")"+returnType.name
}