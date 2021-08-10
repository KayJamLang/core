package com.github.kayjamlang.core.containers

import com.github.kayjamlang.core.AdvancedMutableList
import com.github.kayjamlang.core.exceptions.ParserException
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.opcodes.AccessType

@throws[ParserException]
class ObjectContainer private (name: String, children: AdvancedMutableList[Expression], accessType: AccessType, line: Int, implementsClass: AdvancedMutableList[String], val anonymous: Boolean) extends ClassContainer(name, null, implementsClass, children, accessType, line) {
  def this(children: AdvancedMutableList[Expression], accessType: AccessType, line: Int) {
    this("anonymous@class", children, accessType, line, new AdvancedMutableList[String], true)
    verify()
  }

  def this(name: String, children: AdvancedMutableList[Expression], accessType: AccessType, line: Int) {
    this(name, children, accessType, line, new AdvancedMutableList[String], false)
    verify()

    try
      companion = this.clone.asInstanceOf[ObjectContainer]
    catch {
      case _: CloneNotSupportedException =>
    }

    variables clear()
    functions clear()
  }

  @throws[ParserException]
  private def verify(): Unit = {
    if (companion != null)
      throw new ParserException(companion.line, "Objects cannot have companions")
    if (constructors.nonEmpty)
      throw new ParserException((constructors get 0 get).line, "Objects cannot have constructors")
  }
}
