package com.github.kayjamlang.tests.expressions.callcreate

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.CallOrCreateExpression
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object CallOrCreateExpressionZeroArgsTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("concat()"))
  }
}

class CallOrCreateExpressionZeroArgsTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = CallOrCreateExpressionZeroArgsTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[CallOrCreateExpression], expression getClass)
    val callOrCreateExpression = expression.asInstanceOf[CallOrCreateExpression]
    assertEquals("concat", callOrCreateExpression.name)
    assertEquals(0, callOrCreateExpression.arguments size)
  }
}