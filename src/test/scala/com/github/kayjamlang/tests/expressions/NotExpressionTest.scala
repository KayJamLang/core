package com.github.kayjamlang.tests.expressions

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{NegationExpression, ValueExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}

object NotExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("!true"))
  }
}

class NotExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = NotExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[NegationExpression], expression.getClass)
    val notExpression = expression.asInstanceOf[NegationExpression]
    assertSame(classOf[ValueExpression], notExpression.expression.getClass)
    val constant = notExpression.expression.asInstanceOf[ValueExpression]
    assertEquals(true, constant.value)
  }
}