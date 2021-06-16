package com.github.kayjamlang.tests.expressions

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{CallOrCreateExpression, NamedExpression}
import org.junit.Assert.{assertNotNull, assertSame}
import org.junit.{BeforeClass, Test}

object NamedSingleExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("test -> println()"))
  }
}

class NamedSingleExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = NamedSingleExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[NamedExpression], expression getClass)
    val namedContainer = expression.asInstanceOf[NamedExpression]
    assertSame(classOf[CallOrCreateExpression], namedContainer.expression getClass)
  }
}
