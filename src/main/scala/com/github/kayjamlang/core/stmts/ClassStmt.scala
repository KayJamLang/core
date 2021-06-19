package com.github.kayjamlang.core.stmts

import com.github.kayjamlang.core.ArrayList

class ClassStmt(
                val name: String,
                val classExtends: String,
                val implementsClass: ArrayList[String],
                val methods: ArrayList[FunctionStmt],
                val children: ArrayList[StmtExpression]
               ) extends Stmt {

}
