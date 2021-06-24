package com.github.kayjamlang.tests.expressions.operation

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{CallOrCreateExpression, OperationExpression, ValueExpression}
import com.github.kayjamlang.core.expressions.data.Operation
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object TwoFunctionOperationWithInFunctionContainerOperationTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("equals(2 - 1) + equals(1 + 1)"))
  }
}

class TwoFunctionOperationWithInFunctionContainerOperationTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = TwoFunctionOperationWithInFunctionContainerOperationTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[OperationExpression], expression getClass)
    val operationExpression = expression.asInstanceOf[OperationExpression]
    assertEquals(Operation.PLUS, operationExpression.operation)
    assertSame(classOf[CallOrCreateExpression], operationExpression.left getClass)
    assertSame(classOf[CallOrCreateExpression], operationExpression.right getClass)
    //Left
    val callOrCreateExpressionLeft = operationExpression.left.asInstanceOf[CallOrCreateExpression]
    assertEquals(1, callOrCreateExpressionLeft.arguments.size)
    assertSame(classOf[OperationExpression], callOrCreateExpressionLeft.arguments.head getClass)
    val operationExpressionLeft = callOrCreateExpressionLeft.arguments.head.asInstanceOf[OperationExpression]
    assertEquals(Operation.MINUS, operationExpressionLeft.operation)
    assertSame(classOf[ValueExpression], operationExpressionLeft.left getClass)
    assertSame(classOf[ValueExpression], operationExpressionLeft.right getClass)
    //Right
    val callOrCreateExpressionRight = operationExpression.right.asInstanceOf[CallOrCreateExpression]
    assertEquals(1, callOrCreateExpressionRight.arguments.size)
    assertSame(classOf[OperationExpression], callOrCreateExpressionRight.arguments.head getClass)
    val operationExpressionRight = callOrCreateExpressionRight.arguments.head.asInstanceOf[OperationExpression]
    assertEquals(Operation.PLUS, operationExpressionRight.operation)
    assertSame(classOf[ValueExpression], operationExpressionRight.left getClass)
    assertSame(classOf[ValueExpression], operationExpressionRight.right getClass)
  }
}
