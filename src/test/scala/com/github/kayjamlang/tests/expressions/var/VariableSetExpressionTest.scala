package com.github.kayjamlang.tests.expressions.`var`

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.ValueExpression
import com.github.kayjamlang.core.expressions.VariableSetExpression
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert._


object VariableSetExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("test = 123"))
  }
}

class VariableSetExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = VariableSetExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[VariableSetExpression], expression.getClass)
    val variable = expression.asInstanceOf[VariableSetExpression]
    assertEquals("test", variable.name)
    assertSame(classOf[ValueExpression], variable.expression.getClass)
    val constant = variable.expression.asInstanceOf[ValueExpression]
    assertEquals(123, constant.value)
  }
}
