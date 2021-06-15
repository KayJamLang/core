package com.github.kayjamlang.tests.expressions.ifExpr

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{IfExpression, ValueExpression, VariableLinkExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object IfWithoutExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("if(putin) true"))
  }
}

class IfWithoutExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = IfWithoutExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[IfExpression], expression.getClass)
    val ifExpression = expression.asInstanceOf[IfExpression]
    assertSame(classOf[VariableLinkExpression], ifExpression.condition.getClass)
    assertNotNull(ifExpression.ifTrue)
    assertNull(ifExpression.ifFalse)
    assertSame(classOf[ValueExpression], ifExpression.ifTrue.getClass)
    val root = ifExpression.condition.asInstanceOf[VariableLinkExpression]
    assertEquals("putin", root.name)
    val ifTrue = ifExpression.ifTrue.asInstanceOf[ValueExpression]
    assertEquals(true, ifTrue.value)
  }
}