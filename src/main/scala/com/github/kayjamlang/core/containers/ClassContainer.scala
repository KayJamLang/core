package com.github.kayjamlang.core.containers

import com.github.kayjamlang.core.AdvancedMutableList
import com.github.kayjamlang.core.exceptions.ParserException
import com.github.kayjamlang.core.expressions.{Expression, VariableExpression}
import com.github.kayjamlang.core.opcodes.AccessType

class ClassContainer(val name: String, val extendsClass: String, val implementsClass: AdvancedMutableList[String], children: AdvancedMutableList[Expression], accessType: AccessType, line: Int) extends Container(children, accessType, line) {
  var companion: ObjectContainer = null
  var constructors = new AdvancedMutableList[ConstructorContainer]
  var variables = new AdvancedMutableList[VariableExpression]

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
      case container: FunctionContainer => functions += container
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