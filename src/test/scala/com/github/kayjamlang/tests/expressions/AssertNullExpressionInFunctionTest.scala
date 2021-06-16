package com.github.kayjamlang.tests.expressions

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{AssertNullExpression, CallOrCreateExpression, ValueExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object AssertNullExpressionInFunctionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("test(123!)"))
  }
}

class AssertNullExpressionInFunctionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = AssertNullExpressionInFunctionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[CallOrCreateExpression], expression getClass)
    val callOrCreateExpression = expression.asInstanceOf[CallOrCreateExpression]
    assertEquals(1, callOrCreateExpression.arguments size)
    assertSame(classOf[AssertNullExpression], callOrCreateExpression.arguments.head getClass)
    val assertNull = callOrCreateExpression.arguments.head.asInstanceOf[AssertNullExpression]
    assertSame(classOf[ValueExpression], assertNull.expression getClass)
  }
}