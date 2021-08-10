package com.github.kayjamlang.tests.expressions.callcreate

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{CallOrCreateExpression, ValueExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object CallOrCreateExpressionOneArgsTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("concat(2021)"))
  }
}

class CallOrCreateExpressionOneArgsTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = CallOrCreateExpressionOneArgsTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[CallOrCreateExpression], expression.getClass)
    val callOrCreateExpression = expression.asInstanceOf[CallOrCreateExpression]
    assertEquals("concat", callOrCreateExpression.name)
    assertEquals(1, callOrCreateExpression.arguments.size)
    val firstArgument = callOrCreateExpression.arguments.head
    assertSame(classOf[ValueExpression], firstArgument.getClass)
    val firstArgumentConstant = firstArgument.asInstanceOf[ValueExpression]
    assertEquals(2021, firstArgumentConstant.value)
  }
}