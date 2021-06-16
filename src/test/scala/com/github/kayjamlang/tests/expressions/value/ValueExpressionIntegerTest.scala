package com.github.kayjamlang.tests.expressions.value

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.ValueExpression
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object ValueExpressionIntegerTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("12345"))
  }
}

class ValueExpressionIntegerTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ValueExpressionIntegerTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[ValueExpression], expression getClass)
    val constant = expression.asInstanceOf[ValueExpression]
    assertEquals(12345, constant.value)
  }
}