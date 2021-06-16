package com.github.kayjamlang.tests

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.Type
import com.github.kayjamlang.core.exceptions.LexerException
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertFalse


object VoidTypeTest {
  private var parser: KayJamParser = null

  @BeforeClass
  @throws[LexerException]
  def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("void"))
    parser moveAhead
  }
}

class VoidTypeTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val `type` = VoidTypeTest.parser parseType false
    assertNotEquals(Type VOID, `type`)
    assertFalse(`type`.nullable)
  }
}
