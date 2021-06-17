package com.github.kayjamlang.core.containers

import com.github.kayjamlang.core.AdvancedMutableList
import com.github.kayjamlang.core.exceptions.ParserException
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.opcodes.AccessType

import scala.collection.mutable

class Container(var children: AdvancedMutableList[Expression], identifier: AccessType, line: Int) extends Expression(identifier, line) with Cloneable {
  def this(children: AdvancedMutableList[Expression], line: Int) = this(children, AccessType.NONE, line)

  var functions = new mutable.HashMap[String, FunctionContainer]

  for (expression <- children) {
    expression match {
      case container: FunctionContainer =>
        if(functions.contains(container.desc))
          throw new ParserException(line, s"Function ${container.name} already defined")
        functions += (container.desc, container)
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