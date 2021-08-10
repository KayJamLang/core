package com.github.kayjamlang.tests.containers.functions

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{FunctionRefExpression, ValueExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object FunctionContainerRefZeroArgsExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("-> true"))
  }
}

class FunctionContainerRefZeroArgsExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = FunctionContainerRefZeroArgsExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[FunctionRefExpression], expression.getClass)
    val functionRefExpression = expression.asInstanceOf[FunctionRefExpression]
    assertEquals(0, functionRefExpression.arguments.size)
    assertSame(classOf[ValueExpression], functionRefExpression.expression.getClass)
    val constant = functionRefExpression.expression.asInstanceOf[ValueExpression]
    assertEquals(true, constant.value)
  }
}