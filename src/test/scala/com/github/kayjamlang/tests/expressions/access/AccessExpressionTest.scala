package com.github.kayjamlang.tests.expressions.access

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{AccessExpression, CallOrCreateExpression, VariableLinkExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object AccessExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("getRoot().child"))
  }
}

class AccessExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = AccessExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[AccessExpression], expression.getClass)
    val accessExpression = expression.asInstanceOf[AccessExpression]
    assertSame(classOf[CallOrCreateExpression], accessExpression.root.getClass)
    assertSame(classOf[VariableLinkExpression], accessExpression.child.getClass)
    val root = accessExpression.root.asInstanceOf[CallOrCreateExpression]
    assertEquals("getRoot", root.name)
    assertEquals(0, root.arguments.size)
    val child = accessExpression.child.asInstanceOf[VariableLinkExpression]
    assertEquals("child", child.name)
  }
}