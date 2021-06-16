package com.github.kayjamlang.tests.containers

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.containers.Container
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object ContainerEmptyTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("{}"))
  }
}

class ContainerEmptyTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ContainerEmptyTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[Container], expression getClass)
    val container = expression.asInstanceOf[Container]
    assertEquals(0, container.children size)
  }
}