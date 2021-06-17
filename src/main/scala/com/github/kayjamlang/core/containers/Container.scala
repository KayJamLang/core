package com.github.kayjamlang.core.containers

import com.github.kayjamlang.core.ArrayList
import com.github.kayjamlang.core.exceptions.ParserException
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.opcodes.AccessType

import scala.collection.mutable

class Container(var children: ArrayList[Expression], identifier: AccessType, line: Int, x: Boolean) extends Expression(identifier, line) with Cloneable {
  def this(children: ArrayList[Expression], identifier: AccessType, line: Int) = this(children, identifier, line, true)
  def this(children: ArrayList[Expression], line: Int, x: Boolean = true) = this(children, AccessType.NONE, line, x)

  var functions = new mutable.HashMap[String, FunctionContainer]

  if (x)
    for (expression <- children) {
      expression match {
        case container: FunctionContainer =>
          if (functions.contains(container.desc))
            throw new ParserException(line, s"Function ${container.name} already defined")
          functions put(container.desc, container)
        case _ => children += expression
      }
    }

  @SuppressWarnings(Array("unchecked"))
  @throws[CloneNotSupportedException]
  override def clone: Container = {
    val container = super.clone.asInstanceOf[Container]
    container.children = children.clone()
    container.functions = functions.clone()
    container
  }
}