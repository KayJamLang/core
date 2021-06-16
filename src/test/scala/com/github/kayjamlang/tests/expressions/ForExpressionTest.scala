package com.github.kayjamlang.tests.expressions

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{OperationExpression, ValueExpression}
import com.github.kayjamlang.core.expressions.loops.ForExpression
import org.junit.Assert._
import org.junit.{BeforeClass, Test}

object ForExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("for(test in 0..2) true"))
  }
}

class ForExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ForExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[ForExpression], expression getClass)
    val forExpression = expression.asInstanceOf[ForExpression]
    assertEquals("test", forExpression.variableName)
    assertSame(classOf[OperationExpression], forExpression.range getClass)
    assertSame(classOf[ValueExpression], forExpression.body getClass)
    val condition = forExpression.body.asInstanceOf[ValueExpression]
    assertEquals(true, condition.value)
  }
}