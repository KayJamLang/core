package com.github.kayjamlang.tests.containers.functions

import com.github.kayjamlang.core._
import com.github.kayjamlang.core.containers.FunctionContainer
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object FunctionContainerOneArgsTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("fun test(string value){true;false}"))
  }
}

class FunctionContainerOneArgsTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = FunctionContainerOneArgsTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[FunctionContainer], expression.getClass)
    val functionContainer = expression.asInstanceOf[FunctionContainer]
    assertEquals(1, functionContainer.arguments.size)
    assertEquals(2, functionContainer.children.size)
    val argument = functionContainer.arguments.apply(0)
    assertEquals(Type.STRING, argument.`type`)
    assertEquals("value", argument.name)
  }
}