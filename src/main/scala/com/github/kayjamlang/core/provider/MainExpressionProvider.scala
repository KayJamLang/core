package com.github.kayjamlang.core.provider

import com.github.kayjamlang.core.Type
import com.github.kayjamlang.core.expression.Expression
import com.github.kayjamlang.core.opcodes.AccessType

import java.lang.reflect.InvocationTargetException
import scala.collection.JavaConversions.mapAsJavaMap
import scala.collection.mutable

class MainExpressionProvider[A, B, C](val defaultObject: A, accessType: AccessType) extends Expression(accessType, -1) {
  val providers = new mutable.HashMap[Class[_ <: Expression], ExpressionProvider[_ <: Expression, A, B, C]]

  def addProvider[D <: Expression](expression: Class[D], expressionCompiler: ExpressionProvider[D, A, B, C]): Unit = providers put(expression, expressionCompiler)
  def removeProvider[D <: Expression](expression: Class[D]): Unit = providers remove expression

  @SuppressWarnings(Array("unchecked"))
  @throws[Exception]
  def provide(expression: Expression, context: B, argsContext: B): A = {
    if (providers containsKey expression.getClass) {
      val expressionCompiler = providers get expression.getClass
      val method = expressionCompiler.getClass getMethod("provide", classOf[MainExpressionProvider[_, _, _]], classOf[Any], classOf[Any], classOf[Any])
      try
        return method.invoke(expressionCompiler, this, context.asInstanceOf[Object], argsContext.asInstanceOf[Object], expression).asInstanceOf[A]
      catch {
        case ite: InvocationTargetException =>
          ite getCause match {
            case exception: Exception => throw exception
            case _ => ite.getCause printStackTrace()
          }
      }
    }
    defaultObject
  }

  def getType(expression: Expression, context: B, argsContext: B): Type = {
    if (providers containsKey expression.getClass) {
      val expressionCompiler = providers get expression.getClass
      try {
        val method = expressionCompiler.getClass getMethod("getType", classOf[MainExpressionProvider[_, _, _]], classOf[Any], classOf[Any], classOf[Any])
        return method.invoke(expressionCompiler, this, context.asInstanceOf[Object], argsContext.asInstanceOf[Object], expression).asInstanceOf[Type]
      } catch { case _: Throwable => }
    }
    Type ANY
  }
}
