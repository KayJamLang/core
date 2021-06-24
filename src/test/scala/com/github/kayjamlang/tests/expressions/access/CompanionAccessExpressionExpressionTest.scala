package com.github.kayjamlang.tests.expressions.access

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.expressions.CompanionAccessExpression
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.VariableLinkExpression
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert._


object CompanionAccessExpressionExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("Test::test"))
  }
}

class CompanionAccessExpressionExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = CompanionAccessExpressionExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[CompanionAccessExpression], expression getClass)
    val companionAccessExpression = expression.asInstanceOf[CompanionAccessExpression]
    assertEquals("Test", companionAccessExpression.className)
    assertSame(classOf[VariableLinkExpression], companionAccessExpression.child getClass)
    val variableLinkExpression = companionAccessExpression.child.asInstanceOf[VariableLinkExpression]
    assertEquals("test", variableLinkExpression.name)
  }
}