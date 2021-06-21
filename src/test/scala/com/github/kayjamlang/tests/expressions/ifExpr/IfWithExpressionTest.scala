package com.github.kayjamlang.tests.expressions.ifExpr

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{IfExpression, ValueExpression, VariableLinkExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object IfWithExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer(
      """
        |if (putin)
        | true
        |else
        | false
        |""".stripMargin))
  }
}

class IfWithExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = IfWithExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[IfExpression], expression getClass)
    val ifExpression = expression.asInstanceOf[IfExpression]
    assertSame(classOf[VariableLinkExpression], ifExpression.condition getClass)
    assertNotNull(ifExpression.ifTrue)
    assertNotNull(ifExpression.ifFalse)
    assertSame(classOf[ValueExpression], ifExpression.ifTrue getClass)
    assertSame(classOf[ValueExpression], ifExpression.ifFalse getClass)
    val root = ifExpression.condition.asInstanceOf[VariableLinkExpression]
    assertEquals("putin", root.name)
    val ifTrue = ifExpression.ifTrue.asInstanceOf[ValueExpression]
    assertEquals(true, ifTrue.value)
    val ifFalse = ifExpression.ifFalse.asInstanceOf[ValueExpression]
    assertEquals(false, ifFalse.value)
  }
}