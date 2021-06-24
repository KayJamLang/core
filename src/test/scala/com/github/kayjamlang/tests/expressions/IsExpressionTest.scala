package com.github.kayjamlang.tests.expressions

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser, Type}
import com.github.kayjamlang.core.expressions.{IsExpression, ValueExpression}
import org.junit.Assert.{assertEquals, assertNotNull}
import org.junit.{BeforeClass, Test}

object IsExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("123 is int"))
  }
}

class IsExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = IsExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertEquals(classOf[IsExpression], expression getClass)
    val isExpression = expression.asInstanceOf[IsExpression]
    assertEquals(classOf[ValueExpression], isExpression.expression getClass)
    assertEquals(Type.INTEGER, isExpression.verifyType)
  }
}
