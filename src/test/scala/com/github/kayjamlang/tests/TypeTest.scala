package com.github.kayjamlang.tests

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.Type
import com.github.kayjamlang.core.exceptions.LexerException
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse


object TypeTest {
  private var parser: KayJamParser = null

  @BeforeClass
  @throws[LexerException]
  def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("string"))
    parser.moveAhead
  }
}

class TypeTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val `type` = TypeTest.parser.parseType(false)
    assertEquals(Type.STRING, `type`)
    assertFalse(`type`.nullable)
  }
}