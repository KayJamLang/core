package com.github.kayjamlang.tests

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.Type
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert._


object NullableTypeTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("string?"))
  }
}

class NullableTypeTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val `type` = NullableTypeTest.parser.parseType(false)
    assertEquals(Type.STRING, `type`)
    assertTrue(`type`.nullable)
  }
}
