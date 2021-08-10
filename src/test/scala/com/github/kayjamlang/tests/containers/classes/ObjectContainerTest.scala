package com.github.kayjamlang.tests.containers.classes

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.containers.ObjectContainer
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object ObjectContainerTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("object Test{var test = 123;fun test(){return test}}"))
  }
}

class ObjectContainerTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ObjectContainerTest.parser.readTopExpression
    assertNotNull(expression)
    assertSame(classOf[ObjectContainer], expression.getClass)
    var objectContainer = expression.asInstanceOf[ObjectContainer]
    assertEquals("Test", objectContainer.name)
    assertEquals(0, objectContainer.variables.size)
    assertEquals(0, objectContainer.functions.size)
    objectContainer = objectContainer.companion
    assertNotNull(objectContainer)
    assertEquals(1, objectContainer.variables.size)
    assertEquals(1, objectContainer.functions.size)
  }
}