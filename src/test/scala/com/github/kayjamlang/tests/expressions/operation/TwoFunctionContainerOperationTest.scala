package com.github.kayjamlang.tests.expressions.operation

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{CallOrCreateExpression, OperationExpression}
import com.github.kayjamlang.core.expressions.data.Operation
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object TwoFunctionContainerOperationTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("equals(2)+equals(1)"))
  }
}

class TwoFunctionContainerOperationTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = TwoFunctionContainerOperationTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[OperationExpression], expression.getClass)
    val operationExpression = expression.asInstanceOf[OperationExpression]
    assertEquals(Operation.PLUS, operationExpression.operation)
    assertSame(classOf[CallOrCreateExpression], operationExpression.left.getClass)
    assertSame(classOf[CallOrCreateExpression], operationExpression.right.getClass)
  }
}
