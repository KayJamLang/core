package com.github.kayjamlang.core.containers

import com.github.kayjamlang.core.ArrayList
import com.github.kayjamlang.core.exceptions.ParserException
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.opcodes.AccessType

@throws[ParserException]
class ObjectContainer private (name: String, children: ArrayList[Expression], accessType: AccessType, line: Int, implementsClass: ArrayList[String], val anonymous: Boolean) extends ClassContainer(name, null, implementsClass, children, accessType, line) {
  def this(children: ArrayList[Expression], accessType: AccessType, line: Int) {
    this("anonymous@class", children, accessType, line, new ArrayList[String], true)
    verify()
  }

  def this(name: String, children: ArrayList[Expression], accessType: AccessType, line: Int) {
    this(name, children, accessType, line, new ArrayList[String], false)
    verify()

    try
      companion = this.clone.asInstanceOf[ObjectContainer]
    catch {
      case _: CloneNotSupportedException =>
    }

    variables clear()
  }

  @throws[ParserException]
  private def verify(): Unit = {
    if (companion != null)
      throw new ParserException(companion.line, "Objects cannot have companions")
    if (constructors.nonEmpty)
      throw new ParserException((constructors get 0 get).line, "Objects cannot have constructors")
  }
}
