package com.github.kayjamlang.tests.expressions

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{ReturnExpression, ValueExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}

object ReturnExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("return 2005"))
  }
}

class ReturnExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ReturnExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[ReturnExpression], expression getClass)
    val returnExpression = expression.asInstanceOf[ReturnExpression]
    assertSame(classOf[ValueExpression], returnExpression.expression getClass)
    val constant = returnExpression.expression.asInstanceOf[ValueExpression]
    assertEquals(2005, constant.value)
  }
}