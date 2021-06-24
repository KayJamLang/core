package com.github.kayjamlang.tests.expressions.array

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{ArrayExpression, ValueExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object ArrayExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("[1,2,3,4,5]"))
  }
}

class ArrayExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ArrayExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[ArrayExpression], expression getClass)
    val arrayExpression = expression.asInstanceOf[ArrayExpression]
    assertEquals(5, arrayExpression.values size)
    for (i <- arrayExpression.values indices) {
      val value = arrayExpression.values apply i
      assertSame(classOf[ValueExpression], value getClass)
      val constantValue = value.asInstanceOf[ValueExpression]
      assertEquals(i + 1, constantValue.value)
    }
  }
}