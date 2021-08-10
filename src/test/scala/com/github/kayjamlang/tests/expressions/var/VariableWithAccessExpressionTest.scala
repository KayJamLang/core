package com.github.kayjamlang.tests.expressions.`var`

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.expressions._
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert._


object VariableWithAccessExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("var test = getRequest().query[\"test\"]"))
  }
}

class VariableWithAccessExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = VariableWithAccessExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[VariableExpression], expression.getClass)
    val variable = expression.asInstanceOf[VariableExpression]
    assertEquals("test", variable.name)
    assertSame(classOf[GetExpression], variable.expression.getClass)
    val getExpression = variable.expression.asInstanceOf[GetExpression]
    assertSame(classOf[AccessExpression], getExpression.root.getClass)
  }
}
