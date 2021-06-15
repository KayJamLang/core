package com.github.kayjamlang.tests.expressions.value

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{OperationExpression, ValueExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object ValueExpressionNegativeIntegerTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("-12345"))
  }
}

class ValueExpressionNegativeIntegerTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ValueExpressionNegativeIntegerTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[OperationExpression], expression.getClass)
    val operationExpression = expression.asInstanceOf[OperationExpression]
    assertSame(classOf[ValueExpression], operationExpression.left.getClass)
    assertSame(classOf[ValueExpression], operationExpression.right.getClass)
    var constant = operationExpression.right.asInstanceOf[ValueExpression]
    assertEquals(12345, constant.value)
    constant = operationExpression.left.asInstanceOf[ValueExpression]
    assertEquals(-1, constant.value)
  }
}