package com.github.kayjamlang.core.stmts

import com.github.kayjamlang.core.{ArrayList, Type}
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.data.{Annotation, Argument}
import com.github.kayjamlang.core.opcodes.AccessType

class FunctionStmt(
                    val name: String,
                    val body: Expression,
                    val arguments: ArrayList[Argument],
                    val returnType: Type,
                    val accessType: AccessType,
                    val annotations: ArrayList[Annotation]
                  ) extends Stmt {

}
