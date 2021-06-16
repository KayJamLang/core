package com.github.kayjamlang.tests.containers.classes

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.containers.ObjectContainer
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object AnonymousObjectContainerTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("object{var test = 123;fun test(){return test}}"))
  }
}

class AnonymousObjectContainerTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = AnonymousObjectContainerTest.parser.readTopExpression
    assertNotNull(expression)
    assertSame(classOf[ObjectContainer], expression getClass)
    val objectContainer = expression.asInstanceOf[ObjectContainer]
    assertEquals(1, objectContainer.variables size)
    assertEquals(1, objectContainer.functions size)
  }
}