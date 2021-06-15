package com.github.kayjamlang.tests.expressions.callcreate

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.expressions.CallOrCreateExpression
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.ValueExpression
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert._


object CallOrCreateExpressionTwoArgsTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("concat(2021,\"year\")"))
  }
}

class CallOrCreateExpressionTwoArgsTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = CallOrCreateExpressionTwoArgsTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[CallOrCreateExpression], expression.getClass)
    val callOrCreateExpression = expression.asInstanceOf[CallOrCreateExpression]
    assertEquals("concat", callOrCreateExpression.name)
    assertEquals(2, callOrCreateExpression.arguments.size)
    val firstArgument = callOrCreateExpression.arguments.head
    assertSame(classOf[ValueExpression], firstArgument.getClass)
    val firstArgumentConstant = firstArgument.asInstanceOf[ValueExpression]
    assertEquals(2021, firstArgumentConstant.value)
    val secondaryArgument = callOrCreateExpression.arguments.apply(1)
    assertSame(classOf[ValueExpression], secondaryArgument.getClass)
    val secondaryArgumentConstant = secondaryArgument.asInstanceOf[ValueExpression]
    assertEquals("year", secondaryArgumentConstant.value)
  }
}