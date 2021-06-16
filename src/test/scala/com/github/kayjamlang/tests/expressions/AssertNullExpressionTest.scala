package com.github.kayjamlang.tests.expressions

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{AssertNullExpression, ValueExpression}
import org.junit.Assert.{assertNotNull, assertSame}
import org.junit.{BeforeClass, Test}

object AssertNullExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("123!"))
  }
}

class AssertNullExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = AssertNullExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[AssertNullExpression], expression getClass)
    val assertNull = expression.asInstanceOf[AssertNullExpression]
    assertSame(classOf[ValueExpression], assertNull.expression getClass)
  }
}