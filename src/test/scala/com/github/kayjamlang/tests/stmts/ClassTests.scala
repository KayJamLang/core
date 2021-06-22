package com.github.kayjamlang.tests.stmts

import com.github.kayjamlang.core.expressions.{ValueExpression, VariableExpression}
import com.github.kayjamlang.core.opcodes.AccessType
import com.github.kayjamlang.core.stmts.{ClassStmt, StmtExpression}
import com.github.kayjamlang.core.{ArrayList, Type}
import com.github.kayjamlang.tests.TestsUtils
import org.junit.Assert.{assertEquals, assertNotNull, assertNull, assertTrue}
import org.junit.Test

class ClassTests {

  @Test
  def test1(): Unit = { // TODO: please rename test
    val stmt = TestsUtils.parseStmt("class SimpleClass { }")

    assertNotNull(stmt)
    assertEquals(classOf[ClassStmt], stmt.getClass)

    val clazz = stmt.asInstanceOf[ClassStmt]
    assertEquals("SimpleClass", clazz.name)
    assertNull(clazz.classExtends)
    assertTrue(clazz.implementsClass.isEmpty)
    assertTrue(clazz.methods.isEmpty)
    assertTrue(clazz.properties.isEmpty)

    val companion = clazz.companion
    assertNotNull(clazz.companion)
    assertEquals("companion$SimpleClass", companion.name)
    assertEquals("SimpleClass", companion.classExtends)
    assertTrue(companion.implementsClass.isEmpty)
    assertTrue(companion.methods.isEmpty)
    assertTrue(companion.properties.isEmpty)
    assertEquals(companion, companion.companion)
  }

  @Test
  def test2(): Unit = { // TODO: please rename test
    val stmt = TestsUtils.parseStmt(
      """
        |class AdvancedClass {
        | var x;
        | var y: int;
        | var z: double = 5
        | var i = 12
        | var j = 21 var k = 33
        |}
        |""".stripMargin)

    assertNotNull(stmt)
    assertEquals(classOf[ClassStmt], stmt.getClass)

    val clazz = stmt.asInstanceOf[ClassStmt]
    assertEquals("AdvancedClass", clazz.name)
    assertNull(clazz.classExtends)
    assertTrue(clazz.implementsClass.isEmpty)
    assertTrue(clazz.methods.isEmpty)

    val companion = clazz.companion
    assertNotNull(clazz.companion)
    assertEquals("companion$AdvancedClass", companion.name)
    assertEquals("AdvancedClass", companion.classExtends)
    assertTrue(companion.implementsClass.isEmpty)
    assertTrue(companion.methods.isEmpty)
    assertTrue(companion.properties.isEmpty)
    assertEquals(companion, companion.companion)

    val properties = clazz.properties
    assertEquals(6, properties.size)

    assertVariableExpression(properties, 0, "x", Type NOTHING, null)
    assertVariableExpression(properties, 1, "y", Type INTEGER, null)
    assertVariableExpression(properties, 2, "z", Type DOUBLE, 5)
    assertVariableExpression(properties, 3, "i", Type INTEGER, 12)
    assertVariableExpression(properties, 4, "j", Type INTEGER, 21)
    assertVariableExpression(properties, 5, "k", Type INTEGER, 33)
  }

  def assertVariableExpression(arr: ArrayList[StmtExpression], i: Int, name: String, `type`: Type, value: Any): Unit = {
    val a = (arr apply i).expression
    assertTrue(a.isInstanceOf[VariableExpression])
    val b = a.asInstanceOf[VariableExpression]
    assertEquals(name, b.name)
    assertEquals(`type`, b.variableType)
    assertEquals(AccessType PUBLIC, b.accessType)
    assertTrue(b.data isEmpty)
    assertTrue(b.expression.isInstanceOf[ValueExpression])
    val c = b.expression.asInstanceOf[ValueExpression]
    assertEquals(value, c.value)
    assertEquals(`type`, c.`type`)
    assertEquals(AccessType NONE, c.accessType)
    assertTrue(c.data isEmpty)
  }
}
