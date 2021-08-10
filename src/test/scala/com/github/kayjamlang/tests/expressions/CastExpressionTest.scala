package com.github.kayjamlang.tests.expressions

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser, Type}
import com.github.kayjamlang.core.expressions.{CastExpression, ValueExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}

object CastExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("123 as int"))
  }
}

class CastExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = CastExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertEquals(classOf[CastExpression], expression getClass)
    val castExpression = expression.asInstanceOf[CastExpression]
    assertEquals(classOf[ValueExpression], castExpression.expression getClass)
    assertEquals(Type INTEGER, castExpression.castType)
  }
}
