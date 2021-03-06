package com.github.kayjamlang.core

import com.github.kayjamlang.core.Type.NOTHING

import java.util.Objects
import scala.collection.mutable

object Type {
  val INTEGER = new Type("int", false)
  val DOUBLE = new Type("double", false)
  val LONG = new Type("long", false)
  val STRING = new Type("string", false)
  val BOOLEAN = new Type("bool", false)
  val OBJECT = new Type("obj", false)
  val ARRAY = new Type("array", false)
  val VOID = new Type("void", true)
  val FUNCTION_REF = new Type("func", true)
  val RANGE = new Type("range", true)

  val ANY = new Type("any", false)
  val NOTHING = new Type("nothing", false)

  val allTypes: mutable.MutableList[Type] = mutable.MutableList[Type](INTEGER, DOUBLE, LONG, STRING, BOOLEAN, OBJECT, ARRAY, VOID, FUNCTION_REF, RANGE, ANY, NOTHING)

  /**
   * Type creator
   *
   * @param name Type name
   * @return Type
   */
  def getType(name: String): Type = getType(name, isFunction = false, nullable = false)

  /**
   * Type creator
   *
   * @param name       Type name
   * @param isFunction Can return function type
   * @return Type
   */
  def getType(name: String, isFunction: Boolean): Type = getType(name, isFunction, nullable = false)

  def getType(clazz: Class[_]): Type = {
    if (clazz == null)
      return Type NOTHING

    val name = clazz.getName
    if (name == classOf[Null].getName)
      Type NOTHING
    else if (name == classOf[Void].getName)
      Type VOID
    else if (name == classOf[String].getName)
      Type STRING
    else if (name == classOf[Boolean].getName)
      Type BOOLEAN
    else if (name == classOf[Int].getName)
      Type INTEGER
    else if (name == classOf[Long].getName)
      Type LONG
    else if (name == classOf[Double].getName)
      Type DOUBLE
    else if (name == classOf[List[_]].getName || name == classOf[ArrayList[_]].getName || name == classOf[java.util.ArrayList[_]].getName)
      Type ARRAY
    else
      Type ANY
  }

  /**
   * Type creator
   *
   * @param name       Type name
   * @param isFunction Can return function type
   * @param nullable   Is nullable type
   * @return Type
   */
  def getType(name: String, isFunction: Boolean, nullable: Boolean): Type = {
    for (t <- Type.allTypes) {
      val `type` = t clone()
      `type`.nullable = nullable
      `type`.onlyForFunction = isFunction
      if (`type`.name == name || s"\\${`type`.name}" == name)
        return `type`
    }

    val `type` = new Type(name)
    `type`.nullable = nullable
    `type`
  }

  /**
   * Simple type creator
   *
   * @param name     Name of type
   * @param nullable Is nullable
   * @return Returns need type
   */
  def of(name: String, nullable: Boolean): Type = {
    val `type` = new Type(name)
    `type`.nullable = nullable
    `type` clone()
  }

  /**
   * Simple type creator (not nullable)
   *
   * @param name Name of type
   * @return Returns need type
   */
  def of(name: String) = new Type(name)
}

class Type (val name: String, var onlyForFunction: Boolean = false, val primitive: Boolean = false, var nullable: Boolean = false) {

  override def clone(): Type =
    try super.clone.asInstanceOf[Type]
    catch {
      case _: CloneNotSupportedException =>
        val `type` = new Type(name, onlyForFunction, primitive)
        `type`.nullable = nullable
        `type`
    }

  /**
   * Checks if a type supports another type
   *
   * @param type Type for verify
   * @return Returns whether a type supports another type
   */
  def isAccept(`type`: Type): Boolean =
    if (`type` == NOTHING && (`type`.nullable || nullable))
      true
    else
      this == `type`

  override def equals(o: Any): Boolean =
    if (super.equals(o) || hashCode() == o.hashCode())
      true
    else if (o == null || (getClass != o.getClass))
      false
    else
      onlyForFunction == o.asInstanceOf[Type].onlyForFunction && Objects.equals(name, o.asInstanceOf[Type].name)

  override def hashCode: Int = Objects.hash(name.asInstanceOf[Object], onlyForFunction.asInstanceOf[Object], nullable.asInstanceOf[Object])
}