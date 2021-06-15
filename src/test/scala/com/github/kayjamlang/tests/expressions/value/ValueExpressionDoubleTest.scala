package com.github.kayjamlang.tests.expressions.value

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.ValueExpression
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object ValueExpressionDoubleTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("12.4"))
  }
}

class ValueExpressionDoubleTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ValueExpressionDoubleTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[ValueExpression], expression.getClass)
    val constant = expression.asInstanceOf[ValueExpression]
    assertEquals(12.4, constant.value)
  }
}