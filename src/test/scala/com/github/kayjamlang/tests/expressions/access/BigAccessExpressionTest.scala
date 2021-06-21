package com.github.kayjamlang.tests.expressions.access

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{AccessExpression, CallOrCreateExpression, ValueExpression}
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object BigAccessExpressionTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer(
      """
        |print(a()
        | .b(c().d("test")).e()
        |)
        |""".stripMargin))
  }
}

class BigAccessExpressionTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = BigAccessExpressionTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[CallOrCreateExpression], expression getClass)

    var callOrCreateExpression = expression.asInstanceOf[CallOrCreateExpression]
    assertEquals("print", callOrCreateExpression.name)
    assertEquals(1, callOrCreateExpression.arguments size)
    assertSame(classOf[AccessExpression], callOrCreateExpression.arguments.head getClass)

    var accessExpression = callOrCreateExpression.arguments.head.asInstanceOf[AccessExpression]
    assertSame(classOf[AccessExpression], accessExpression.root getClass)
    assertSame(classOf[CallOrCreateExpression], accessExpression.child getClass)
    callOrCreateExpression = accessExpression.child.asInstanceOf[CallOrCreateExpression]
    assertEquals("e", callOrCreateExpression.name)
    assertEquals(0, callOrCreateExpression.arguments size)
    accessExpression = accessExpression.root.asInstanceOf[AccessExpression]
    assertSame(classOf[CallOrCreateExpression], accessExpression.root getClass)
    assertSame(classOf[CallOrCreateExpression], accessExpression.child getClass)
    callOrCreateExpression = accessExpression.root.asInstanceOf[CallOrCreateExpression]
    assertEquals("a", callOrCreateExpression.name)
    assertEquals(0, callOrCreateExpression.arguments size)
    callOrCreateExpression = accessExpression.child.asInstanceOf[CallOrCreateExpression]
    assertEquals("b", callOrCreateExpression.name)
    assertEquals(1, callOrCreateExpression.arguments size)
    assertSame(classOf[AccessExpression], callOrCreateExpression.arguments.head getClass)
    accessExpression = callOrCreateExpression.arguments.head.asInstanceOf[AccessExpression]
    assertSame(classOf[CallOrCreateExpression], accessExpression.root getClass)
    assertSame(classOf[CallOrCreateExpression], accessExpression.child getClass)
    callOrCreateExpression = accessExpression.root.asInstanceOf[CallOrCreateExpression]
    assertEquals("c", callOrCreateExpression.name)
    assertEquals(0, callOrCreateExpression.arguments size)
    callOrCreateExpression = accessExpression.child.asInstanceOf[CallOrCreateExpression]
    assertEquals("d", callOrCreateExpression.name)
    assertEquals(1, callOrCreateExpression.arguments size)
    assertSame(classOf[ValueExpression], callOrCreateExpression.arguments.head getClass)
  }
}