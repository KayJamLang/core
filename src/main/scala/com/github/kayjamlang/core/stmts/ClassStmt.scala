package com.github.kayjamlang.core.stmts

import com.github.kayjamlang.core.ArrayList

class ClassStmt(
                val name: String,
                val classExtends: String,
                val implementsClass: ArrayList[String],
                var methods: ArrayList[FunctionStmt],
                var properties: ArrayList[StmtExpression],
                var companion: ObjectStmt
               ) extends Stmt {
  @SuppressWarnings(Array("unchecked"))
  @throws[CloneNotSupportedException]
  override def clone: ClassStmt = {
    val classContainer = super.clone.asInstanceOf[ClassStmt]
    classContainer.methods = methods clone()
    classContainer.properties = properties clone()
    classContainer
  }

  override def toString: String = s"$name@class"
}
