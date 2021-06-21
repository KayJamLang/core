package com.github.kayjamlang.tests.expressions

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.ValueExpression
import com.github.kayjamlang.core.expressions.loops.WhileExpression
import org.junit.Assert._
import org.junit.{BeforeClass, Test}

object WhileExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer(
      """
        |while (false)
        | true
        |""".stripMargin))
  }
}

class WhileExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = WhileExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[WhileExpression], expression getClass)
    val whileExpression = expression.asInstanceOf[WhileExpression]
    assertSame(classOf[ValueExpression], whileExpression.condition getClass)
    assertSame(classOf[ValueExpression], whileExpression.expression getClass)
    val condition = whileExpression.condition.asInstanceOf[ValueExpression]
    assertEquals(false, condition.value)
    val cValue = whileExpression.expression.asInstanceOf[ValueExpression]
    assertEquals(true, cValue.value)
  }
}