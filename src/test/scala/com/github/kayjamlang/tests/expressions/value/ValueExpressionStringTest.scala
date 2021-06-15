package com.github.kayjamlang.tests.expressions.value

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.ValueExpression
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert._


object ValueExpressionStringTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("\"test\""))
  }
}

class ValueExpressionStringTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ValueExpressionStringTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[ValueExpression], expression.getClass)
    val constant = expression.asInstanceOf[ValueExpression]
    assertEquals("test", constant.value)
  }
}