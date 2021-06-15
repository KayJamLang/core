package com.github.kayjamlang.tests.containers

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.containers.NamedExpressionFunctionContainer
import com.github.kayjamlang.core.expressions.Expression
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert._


object NamedExpressionFunctionContainerTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("named fun test{}"))
  }
}

class NamedExpressionFunctionContainerTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = NamedExpressionFunctionContainerTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[NamedExpressionFunctionContainer], expression.getClass)
    val namedExpressionFunction = expression.asInstanceOf[NamedExpressionFunctionContainer]
    assertEquals(0, namedExpressionFunction.children.size)
  }
}
