package com.github.kayjamlang.core.containers

import com.github.kayjamlang.core.ArrayList
import com.github.kayjamlang.core.exceptions.ParserException
import com.github.kayjamlang.core.expressions.{ConstantValueExpression, Expression, UseExpression}
import com.github.kayjamlang.core.opcodes.AccessType

import scala.collection.JavaConversions.asJavaCollection
import scala.collection.mutable
import scala.util.control.Breaks.break

class PackContainer(val packName: String, container: Container, otherExpressionAllow: Boolean) extends Container(new ArrayList[Expression], AccessType.NONE, 0) {
  val constants = new mutable.HashMap[String, Any]
  val packs = new mutable.HashMap[String, PackContainer]
  val classes = new mutable.HashMap[String, ClassContainer]
  val usages = new mutable.MutableList[UseExpression]

  functions.addAll(container.functions)

  var head = 0

  import scala.collection.JavaConversions._

  for (expression <- container.children) {
    if (expression.isInstanceOf[UseExpression]) {
      if (head != 0)
        throw new ParserException(expression.line, "All use expressions must be above the rest!")
      usages add expression.asInstanceOf
      break
    }
    head += 1
    expression match {
      case constant: ConstantValueExpression =>
        if (head == 0)
          head += 1
        else if (head != 1)
          throw new ParserException(expression.line, "All constant expressions must be above the rest!")
        if (constants containsKey constant.name)
          throw new ParserException(expression.line, "Constant \"" + constant.name + "\" already defined")
        constants put(constant.name, constant.value.value)
      case clazz: ClassContainer =>
        classes put(clazz.name, clazz)
      case pack: PackContainer =>
        packs put(pack.packName, pack)
      case _ => if (otherExpressionAllow) children add expression
    }
  }
}
