package com.github.kayjamlang.tests.expressions.operation

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{OperationExpression, ValueExpression}
import com.github.kayjamlang.core.expressions.data.Operation
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object ShortOperationTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("2++"))
  }
}

class ShortOperationTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ShortOperationTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[OperationExpression], expression.getClass)
    val operationExpression = expression.asInstanceOf[OperationExpression]
    assertEquals(Operation.PLUS, operationExpression.operation)
    assertSame(classOf[ValueExpression], operationExpression.left.getClass)
    assertSame(classOf[ValueExpression], operationExpression.right.getClass)
    val right = operationExpression.right.asInstanceOf[ValueExpression]
    assertEquals(right.value, 1)
  }
}
