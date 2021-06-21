package com.github.kayjamlang.tests.stmts

import com.github.kayjamlang.core.Type
import com.github.kayjamlang.core.expressions.{ValueExpression, VariableExpression}
import com.github.kayjamlang.core.opcodes.AccessType
import com.github.kayjamlang.core.stmts.ClassStmt
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
        | var k = 33
        | var i = 12 var j = 21
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
    assertEquals(4, properties.size)

    val p1 = properties.head.expression
    assertTrue(p1.isInstanceOf[VariableExpression])

    val x = p1.asInstanceOf[VariableExpression]
    assertEquals("x", x.name)
    assertEquals(Type NOTHING, x.variableType)
    assertEquals(AccessType PUBLIC, x.accessType)
    assertTrue(x.data isEmpty)

    assertTrue(x.expression.isInstanceOf[ValueExpression])
    val xE = x.expression.asInstanceOf[ValueExpression]
    assertEquals(null, xE.value)
    assertEquals(Type NOTHING, xE.`type`)
    assertEquals(AccessType NONE, xE.accessType)
    assertTrue(xE.data isEmpty)

    val p2 = properties.apply(1).expression
    assertTrue(p2.isInstanceOf[VariableExpression])

    val k = p2.asInstanceOf[VariableExpression]
    assertEquals("k", k.name)
    assertEquals(Type INTEGER, k.variableType)
    assertEquals(AccessType PUBLIC, k.accessType)
    assertTrue(k.data isEmpty)

    assertTrue(k.expression.isInstanceOf[ValueExpression])
    val kE = k.expression.asInstanceOf[ValueExpression]
    assertEquals(33, kE.value)
    assertEquals(Type INTEGER, kE.`type`)
    assertEquals(AccessType NONE, kE.accessType)
    assertTrue(kE.data isEmpty)

    val p3 = properties.apply(2).expression
    assertTrue(p2.isInstanceOf[VariableExpression])

    val i = p3.asInstanceOf[VariableExpression]
    assertEquals("i", i.name)
    assertEquals(Type INTEGER, i.variableType)
    assertEquals(AccessType PUBLIC, i.accessType)
    assertTrue(i.data isEmpty)

    assertTrue(i.expression.isInstanceOf[ValueExpression])
    val iE = i.expression.asInstanceOf[ValueExpression]
    assertEquals(12, iE.value)
    assertEquals(Type INTEGER, iE.`type`)
    assertEquals(AccessType NONE, iE.accessType)
    assertTrue(iE.data isEmpty)

    val p4 = properties.apply(3).expression
    assertTrue(p2.isInstanceOf[VariableExpression])

    val j = p4.asInstanceOf[VariableExpression]
    assertEquals("j", j.name)
    assertEquals(Type INTEGER, j.variableType)
    assertEquals(AccessType PUBLIC, j.accessType)
    assertTrue(j.data isEmpty)

    assertTrue(j.expression.isInstanceOf[ValueExpression])
    val jE = j.expression.asInstanceOf[ValueExpression]
    assertEquals(21, jE.value)
    assertEquals(Type INTEGER, jE.`type`)
    assertEquals(AccessType NONE, jE.accessType)
    assertTrue(jE.data isEmpty)
  }
}
