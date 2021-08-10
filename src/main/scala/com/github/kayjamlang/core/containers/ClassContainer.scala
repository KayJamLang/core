package com.github.kayjamlang.core.containers

import com.github.kayjamlang.core.ArrayList
import com.github.kayjamlang.core.exceptions.ParserException
import com.github.kayjamlang.core.expressions.{Expression, VariableExpression}
import com.github.kayjamlang.core.opcodes.AccessType

@deprecated("Use stmt version")
class ClassContainer(val name: String, val extendsClass: String, val implementsClass: ArrayList[String], children: ArrayList[Expression], accessType: AccessType, line: Int) extends Container(children, accessType, line, false) {
  var companion: ObjectContainer = null
  var constructors = new ArrayList[ConstructorContainer]
  var variables = new ArrayList[VariableExpression]

  for (expression <- children) {
    expression match {
      case container: ObjectContainer if expression.accessType eq AccessType.COMPANION =>
        if (companion != null)
          throw new ParserException(expression.line, "companion already exists")
        companion = container
        children -= expression
      case container: ConstructorContainer =>
        constructors += container
        children -= expression
      case expression1: VariableExpression => variables += expression1
      case container: FunctionContainer =>
        if(functions.contains(container.desc))
          throw new ParserException(line, s"Function ${container.name} already defined")
        functions put(container.desc, container)
      case _ => throw new ParserException(line, "The class can only contain variables and functions")
    }
  }

  @SuppressWarnings(Array("unchecked"))
  @throws[CloneNotSupportedException]
  override def clone: ClassContainer = {
    val classContainer = super.clone.asInstanceOf[ClassContainer]
    classContainer.constructors = constructors clone()
    classContainer.variables = variables clone()
    classContainer
  }

  override def toString: String = s"$name@class"
}