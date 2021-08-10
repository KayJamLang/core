package com.github.kayjamlang.core.stmts

import com.github.kayjamlang.core.ArrayList

class ObjectStmt(
                  name: String,
                  clazz: String,
                  implementsClass: ArrayList[String],
                  methods: ArrayList[FunctionStmt],
                  children: ArrayList[StmtExpression]
                ) extends ClassStmt(name, clazz, implementsClass, methods, children, null) {
  companion = this
}
