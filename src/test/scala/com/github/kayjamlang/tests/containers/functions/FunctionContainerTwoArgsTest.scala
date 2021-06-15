package com.github.kayjamlang.tests.containers.functions

import com.github.kayjamlang.core._
import com.github.kayjamlang.core.containers.FunctionContainer
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object FunctionContainerTwoArgsTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("fun test(string value, int integer){true;false}"))
  }
}

class FunctionContainerTwoArgsTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = FunctionContainerTwoArgsTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[FunctionContainer], expression.getClass)
    val functionContainer = expression.asInstanceOf[FunctionContainer]
    assertEquals(2, functionContainer.arguments.size)
    assertEquals(2, functionContainer.children.size)
    var argument = functionContainer.arguments.head
    assertEquals(Type.STRING, argument.`type`)
    assertEquals("value", argument.name)
    argument = functionContainer.arguments.apply(1)
    assertEquals(Type.INTEGER, argument.`type`)
    assertEquals("integer", argument.name)
  }
}