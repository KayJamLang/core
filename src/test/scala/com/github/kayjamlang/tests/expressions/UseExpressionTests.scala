package com.github.kayjamlang.tests.expressions

import com.github.kayjamlang.core.exceptions.{LexerException, ParserException}
import com.github.kayjamlang.core.expressions.UseExpression
import com.github.kayjamlang.tests.TestsUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class UseExpressionTests {
  @Test
  @throws[ParserException]
  @throws[LexerException]
  def parseSingleUse(): Unit = {
    val expression = TestsUtils parse "use Test\\Value from \"test.kj\""
    assertEquals(classOf[UseExpression], expression getClass)
    val useExpression = expression.asInstanceOf[UseExpression]
    assertEquals("test.kj", useExpression.from)
    assertEquals(1, useExpression.required size)
    assertEquals("Test\\Value", useExpression.required head)
  }

  @Test
  @throws[ParserException]
  @throws[LexerException]
  def parseMultiSingleUse(): Unit = {
    val expression = TestsUtils parse "use Test{ Value, ValueT } from \"test.kj\""
    assertEquals(classOf[UseExpression], expression getClass)
    val useExpression = expression.asInstanceOf[UseExpression]
    assertEquals("test.kj", useExpression.from)
    assertEquals(2, useExpression.required size)
    assertEquals("Test\\Value", useExpression.required head)
    assertEquals("Test\\ValueT", useExpression.required apply 1)
  }

  @Test
  @throws[ParserException]
  @throws[LexerException]
  def parseMultiUse(): Unit = {
    val expression = TestsUtils parse "use { Value, ValueT } from \"test.kj\""
    assertEquals(classOf[UseExpression], expression getClass)
    val useExpression = expression.asInstanceOf[UseExpression]
    assertEquals("test.kj", useExpression.from)
    assertEquals(2, useExpression.required size)
    assertEquals("Value", useExpression.required head)
    assertEquals("ValueT", useExpression.required apply 1)
  }
}