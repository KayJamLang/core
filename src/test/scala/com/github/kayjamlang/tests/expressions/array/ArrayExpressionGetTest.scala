package com.github.kayjamlang.tests.expressions.array

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{GetExpression, ValueExpression, VariableLinkExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object ArrayExpressionGetTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("test[2005]"))
  }
}

class ArrayExpressionGetTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ArrayExpressionGetTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[GetExpression], expression.getClass)
    val arrayGet = expression.asInstanceOf[GetExpression]
    assertSame(classOf[VariableLinkExpression], arrayGet.root.getClass)
    assertSame(classOf[ValueExpression], arrayGet.value.getClass)
    val variableLinkExpression = arrayGet.root.asInstanceOf[VariableLinkExpression]
    assertEquals("test", variableLinkExpression.name)
    val constant = arrayGet.value.asInstanceOf[ValueExpression]
    assertEquals(2005, constant.value)
  }
}