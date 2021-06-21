package com.github.kayjamlang.tests.containers

import com.github.kayjamlang.core.containers.PackContainer
import com.github.kayjamlang.core.exceptions.{LexerException, ParserException}
import com.github.kayjamlang.tests.TestsUtils
import org.junit.Assert.assertEquals
import org.junit.Test


class PackContainerTests {
  @Test
  @throws[ParserException]
  @throws[LexerException]
  def constantTest(): Unit = {
    val expression = TestsUtils.parse(
      """
        |pack Test\A {
        | const test = 123;
        |}
        |""".stripMargin)
    assertEquals(classOf[PackContainer], expression getClass)
    val pack = expression.asInstanceOf[PackContainer]
    assertEquals(1, pack.constants size)
    assertEquals(123, pack constants "test")
  }
}
