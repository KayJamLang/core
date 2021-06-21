package com.github.kayjamlang.tests.expressions.access

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.expressions._
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert._


object AccessExpressionWithOperationsTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("getRoot().child + 123"))
  }
}

class AccessExpressionWithOperationsTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = AccessExpressionWithOperationsTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[OperationExpression], expression getClass)
    val operationExpression = expression.asInstanceOf[OperationExpression]
    assertSame(classOf[AccessExpression], operationExpression.left getClass)
    assertSame(classOf[ValueExpression], operationExpression.right getClass)
    val accessExpression = operationExpression.left.asInstanceOf[AccessExpression]
    assertSame(classOf[CallOrCreateExpression], accessExpression.root getClass)
    assertSame(classOf[VariableLinkExpression], accessExpression.child getClass)
    val root = accessExpression.root.asInstanceOf[CallOrCreateExpression]
    assertEquals("getRoot", root.name)
    assertEquals(0, root.arguments size)
    val child = accessExpression.child.asInstanceOf[VariableLinkExpression]
    assertEquals("child", child.name)
  }
}