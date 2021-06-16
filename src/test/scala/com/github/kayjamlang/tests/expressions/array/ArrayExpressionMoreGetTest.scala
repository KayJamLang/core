package com.github.kayjamlang.tests.expressions.array

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{GetExpression, ValueExpression, VariableLinkExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object ArrayExpressionMoreGetTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("test[2005][2006]"))
  }
}

class ArrayExpressionMoreGetTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ArrayExpressionMoreGetTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[GetExpression], expression getClass)
    val arrayGet = expression.asInstanceOf[GetExpression]
    assertSame(classOf[GetExpression], arrayGet.root getClass)
    assertSame(classOf[ValueExpression], arrayGet.value getClass)
    val getExpression = arrayGet.root.asInstanceOf[GetExpression]
    assertSame(classOf[VariableLinkExpression], getExpression.root getClass)
    assertSame(classOf[ValueExpression], getExpression.value getClass)
    val constant = arrayGet.value.asInstanceOf[ValueExpression]
    assertEquals(2006, constant.value)
  }
}