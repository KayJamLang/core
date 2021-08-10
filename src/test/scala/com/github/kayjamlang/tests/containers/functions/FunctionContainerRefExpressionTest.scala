package com.github.kayjamlang.tests.containers.functions

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser, Type}
import com.github.kayjamlang.core.expressions.{FunctionRefExpression, ValueExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object FunctionContainerRefExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("-> (test) true"))
  }
}

class FunctionContainerRefExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = FunctionContainerRefExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[FunctionRefExpression], expression.getClass)
    val functionRefExpression = expression.asInstanceOf[FunctionRefExpression]
    assertEquals(1, functionRefExpression.arguments.size)
    assertEquals("test", functionRefExpression.arguments.head.name)
    assertEquals(Type.ANY, functionRefExpression.arguments.head.`type`)
    assertSame(classOf[ValueExpression], functionRefExpression.expression.getClass)
    val constant = functionRefExpression.expression.asInstanceOf[ValueExpression]
    assertEquals(true, constant.value)
  }
}