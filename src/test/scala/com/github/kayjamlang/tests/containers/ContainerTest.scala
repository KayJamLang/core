package com.github.kayjamlang.tests.containers

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.containers.Container
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object ContainerTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer(
      """
        |{
        | true;
        | false;
        | var test = 12345;
        |}
        |""".stripMargin))
  }
}

class ContainerTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ContainerTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[Container], expression getClass)
    val container = expression.asInstanceOf[Container]
    assertEquals(3, container.children size)
  }
}