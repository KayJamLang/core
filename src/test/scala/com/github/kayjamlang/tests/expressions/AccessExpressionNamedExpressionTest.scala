package com.github.kayjamlang.tests.expressions

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.containers.Container
import com.github.kayjamlang.core.expressions.{AccessExpression, NamedExpression, VariableLinkExpression}
import org.junit.Assert.{assertNotNull, assertSame}
import org.junit.{BeforeClass, Test}


object AccessExpressionNamedExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("test.test {}"))
  }
}

class AccessExpressionNamedExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = AccessExpressionNamedExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[AccessExpression], expression getClass)
    val accessExpression = expression.asInstanceOf[AccessExpression]
    assertSame(classOf[VariableLinkExpression], accessExpression.root getClass)
    assertSame(classOf[NamedExpression], accessExpression.child getClass)
    val namedContainer = accessExpression.child.asInstanceOf[NamedExpression]
    assertSame(classOf[Container], namedContainer.expression getClass)
  }
}