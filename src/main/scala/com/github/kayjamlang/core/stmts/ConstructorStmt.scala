package com.github.kayjamlang.core.stmts

import com.github.kayjamlang.core.{ArrayList, Type}
import com.github.kayjamlang.core.containers.{ConstructorContainer, Container}
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.data.{Annotation, Argument}
import com.github.kayjamlang.core.opcodes.AccessType

class ConstructorStmt(
                       arguments: ArrayList[Argument],
                       body: Expression,
                       returnType: Type,
                       accessType: AccessType,
                       annotations: ArrayList[Annotation]
                     ) extends FunctionStmt("<init>", arguments, body, returnType, accessType, annotations) {
  def this(source: ConstructorContainer) {
    this(source.arguments, new Container(source.children, AccessType NONE, source.line, false), Type OBJECT, AccessType NONE, new ArrayList[Annotation])
  }
}