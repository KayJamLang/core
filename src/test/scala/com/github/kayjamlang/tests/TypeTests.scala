package com.github.kayjamlang.tests

import com.github.kayjamlang.core.Type
import com.github.kayjamlang.core.exceptions.LexerException
import com.github.kayjamlang.core.exceptions.ParserException
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals


class TypeTests {
  @Test
  @throws[ParserException]
  @throws[LexerException]
  def primitiveTypeParse(): Unit = {
    val `type` = TestsUtils.getParser("int").parseType(false)
    assertEquals(Type.INTEGER, `type`)
  }

  @Test
  @throws[ParserException]
  @throws[LexerException]
  def primitiveTypeForFunctionParse(): Unit = {
    val `type` = TestsUtils.getParser("void").parseType(true)
    assertEquals(Type.VOID, `type`)
  }

  @Test
  @throws[ParserException]
  @throws[LexerException]
  def typeParse(): Unit = {
    val `type` = TestsUtils.getParser("test").parseType(true)
    assertEquals("\\test", `type`.name)
  }

  @Test
  @throws[ParserException]
  @throws[LexerException]
  def multiTypeParse(): Unit = {
    val `type` = TestsUtils.getParser("test\\testType").parseType(true)
    assertEquals("\\test\\testType", `type`.name)
  }

  @Test
  @throws[ParserException]
  @throws[LexerException]
  def multiTypeParseWithStart(): Unit = {
    val `type` = TestsUtils.getParser("\\test\\testType").parseType(true)
    assertEquals("\\test\\testType", `type`.name)
  }

  @Test
  @throws[ParserException]
  @throws[LexerException]
  def primitiveTypeForFunctionParseException(): Unit = {
    val `type` = TestsUtils.getParser("void").parseType(false)
    assertNotEquals(Type.VOID, `type`)
  }
}
