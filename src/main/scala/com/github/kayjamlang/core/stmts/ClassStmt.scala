package com.github.kayjamlang.core.stmts

import com.github.kayjamlang.core.ArrayList

class ClassStmt(
                val name: String,
                val classExtends: String,
                val implementsClass: ArrayList[String]
               ) extends Stmt {

}
