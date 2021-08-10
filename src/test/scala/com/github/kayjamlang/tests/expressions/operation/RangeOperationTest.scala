package com.github.kayjamlang.tests.expressions.operation

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.expressions.CallOrCreateExpression
import com.github.kayjamlang.core.expressions.Expression
import com.github.kayjamlang.core.expressions.OperationExpression
import com.github.kayjamlang.core.expressions.ValueExpression
import com.github.kayjamlang.core.expressions.data.Operation
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert._


object RangeOperationTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("equals(2..10)"))
  }
}

class RangeOperationTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = RangeOperationTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[CallOrCreateExpression], expression.getClass)
    val callOrCreateExpression = expression.asInstanceOf[CallOrCreateExpression]
    assertEquals(1, callOrCreateExpression.arguments.size)
    assertSame(classOf[OperationExpression], callOrCreateExpression.arguments.head.getClass)
    val operationExpression = callOrCreateExpression.arguments.head.asInstanceOf[OperationExpression]
    assertEquals(Operation.RANGE, operationExpression.operation)
    assertSame(classOf[ValueExpression], operationExpression.left.getClass)
    assertSame(classOf[ValueExpression], operationExpression.right.getClass)
  }
}
