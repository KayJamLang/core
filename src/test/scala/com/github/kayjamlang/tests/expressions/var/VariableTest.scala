package com.github.kayjamlang.tests.expressions.`var`

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{ValueExpression, VariableExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object VariableTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("var test = 123"))
  }
}

class VariableTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = VariableTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[VariableExpression], expression getClass)
    val variable = expression.asInstanceOf[VariableExpression]
    assertEquals("test", variable.name)
    assertSame(classOf[ValueExpression], variable.expression getClass)
    val constant = variable.expression.asInstanceOf[ValueExpression]
    assertEquals(123, constant.value)
  }
}
