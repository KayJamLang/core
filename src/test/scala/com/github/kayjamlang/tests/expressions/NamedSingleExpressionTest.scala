package com.github.kayjamlang.tests.expressions

import com.github.kayjamlang.core.containers.Container
import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.expressions.{CallOrCreateExpression, FunctionRefExpression, NamedExpression}
import org.junit.Assert.{assertNotNull, assertSame}
import org.junit.{BeforeClass, Test}

class NamedSingleExpressionTest {
  @Test
  @throws[Exception]
  def test1(): Unit = { // TODO: rename test!
    val parser = new KayJamParser(new KayJamLexer("test -> (x) { println(x) }"))

    val expression = parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[NamedExpression], expression getClass)

    val namedContainer = expression.asInstanceOf[NamedExpression]
    assertSame(classOf[FunctionRefExpression], namedContainer.expression getClass)

    val functionRefContainer = namedContainer.expression.asInstanceOf[FunctionRefExpression]
    assertSame(classOf[Container], functionRefContainer.expression getClass)

    val container = functionRefContainer.expression.asInstanceOf[Container]
    assertSame(classOf[CallOrCreateExpression], container.children.head getClass)
  }

  @Test
  @throws[Exception]
  def test2(): Unit = { // TODO: rename test!
    val parser = new KayJamParser(new KayJamLexer("test -> { println() }"))

    val expression = parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[NamedExpression], expression getClass)

    val namedContainer = expression.asInstanceOf[NamedExpression]
    assertSame(classOf[FunctionRefExpression], namedContainer.expression getClass)

    val functionRefContainer = namedContainer.expression.asInstanceOf[FunctionRefExpression]
    assertSame(classOf[Container], functionRefContainer.expression getClass)

    val container = functionRefContainer.expression.asInstanceOf[Container]
    assertSame(classOf[CallOrCreateExpression], container.children.head getClass)
  }

  @Test
  @throws[Exception]
  def test3(): Unit = { // TODO: rename test!
    val parser = new KayJamParser(new KayJamLexer("test -> println()"))

    val expression = parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[NamedExpression], expression getClass)

    val namedContainer = expression.asInstanceOf[NamedExpression]
    assertSame(classOf[FunctionRefExpression], namedContainer.expression getClass)

    val functionRefContainer = namedContainer.expression.asInstanceOf[FunctionRefExpression]
    assertSame(classOf[CallOrCreateExpression], functionRefContainer.expression getClass)
  }
}
