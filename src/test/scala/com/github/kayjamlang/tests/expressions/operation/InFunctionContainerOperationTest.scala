package com.github.kayjamlang.tests.expressions.operation

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{CallOrCreateExpression, OperationExpression, ValueExpression}
import com.github.kayjamlang.core.expressions.data.Operation
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object InFunctionContainerOperationTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("equals(2+2)"))
  }
}

class InFunctionContainerOperationTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = InFunctionContainerOperationTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[CallOrCreateExpression], expression.getClass)
    val callOrCreateExpression = expression.asInstanceOf[CallOrCreateExpression]
    assertEquals(1, callOrCreateExpression.arguments.size)
    assertSame(classOf[OperationExpression], callOrCreateExpression.arguments.head.getClass)
    val operationExpression = callOrCreateExpression.arguments.head.asInstanceOf[OperationExpression]
    assertEquals(Operation.PLUS, operationExpression.operation)
    assertSame(classOf[ValueExpression], operationExpression.left.getClass)
    assertSame(classOf[ValueExpression], operationExpression.right.getClass)
  }
}
