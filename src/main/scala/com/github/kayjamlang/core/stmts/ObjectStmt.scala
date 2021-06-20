package com.github.kayjamlang.core.stmts

import com.github.kayjamlang.core.ArrayList

class ObjectStmt(
                  name: String,
                  clazz: String,
                  implementsClass: ArrayList[String],
                  methods: ArrayList[FunctionStmt],
                  properties: ArrayList[StmtExpression]
                ) extends ClassStmt(name, clazz, implementsClass, methods, properties, null) {
  companion = this
}
