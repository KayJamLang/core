package com.github.kayjamlang.core.stmts

import com.github.kayjamlang.core.{ArrayList, Type}
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.data.{Annotation, Argument}
import com.github.kayjamlang.core.opcodes.AccessType

class FunctionStmt(
                    val name: String,
                    val arguments: ArrayList[Argument],
                    val body: Expression,
                    val returnType: Type,
                    val accessType: AccessType,
                    val annotations: ArrayList[Annotation]
                  ) extends Stmt {
  val desc: String = name+"("+{
    var args = ""
    arguments.foreach {
      argument: Argument => args += argument.`type`.name
    }

    args
  }+")"+returnType.name
}
