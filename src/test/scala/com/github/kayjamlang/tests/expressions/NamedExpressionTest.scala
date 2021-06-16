package com.github.kayjamlang.tests.expressions

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.containers.Container
import com.github.kayjamlang.core.expressions.NamedExpression
import org.junit.Assert._
import org.junit.{BeforeClass, Test}

object NamedExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("test{}"))
  }
}

class NamedExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = NamedExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[NamedExpression], expression getClass)
    val namedContainer = expression.asInstanceOf[NamedExpression]
    assertSame(classOf[Container], namedContainer.expression getClass)
  }
}