package com.github.kayjamlang.tests.expressions.`var`

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.VariableLinkExpression
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert._


object VariableLinkTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("test"))
  }
}

class VariableLinkTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = VariableLinkTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[VariableLinkExpression], expression.getClass)
    val variableLinkExpression = expression.asInstanceOf[VariableLinkExpression]
    assertEquals("test", variableLinkExpression.name)
  }
}
