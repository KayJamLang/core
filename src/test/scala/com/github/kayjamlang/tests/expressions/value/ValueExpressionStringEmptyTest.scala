package com.github.kayjamlang.tests.expressions.value

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.ValueExpression
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert._


object ValueExpressionStringEmptyTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("\"\""))
  }
}

class ValueExpressionStringEmptyTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ValueExpressionStringEmptyTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[ValueExpression], expression.getClass)
    val constant = expression.asInstanceOf[ValueExpression]
    assertEquals("", constant.value)
  }
}