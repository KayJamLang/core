package com.github.kayjamlang.tests.containers.functions

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.containers.FunctionContainer
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object FunctionContainerZeroArgsTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("fun test(){true;false}"))
  }
}

class FunctionContainerZeroArgsTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = FunctionContainerZeroArgsTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[FunctionContainer], expression.getClass)
    val functionContainer = expression.asInstanceOf[FunctionContainer]
    assertEquals(0, functionContainer.arguments.size)
    assertEquals(2, functionContainer.children.size)
  }
}