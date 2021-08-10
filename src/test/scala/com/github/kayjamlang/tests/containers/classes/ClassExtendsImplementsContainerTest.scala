package com.github.kayjamlang.tests.containers.classes

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import com.github.kayjamlang.core.containers.ClassContainer
import org.junit.Assert._
import org.junit.{BeforeClass, Test}


object ClassExtendsImplementsContainerTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer("class Test: ABC:: AAA {var test = 123;fun test(){return test}}"))
  }
}

class ClassExtendsImplementsContainerTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = ClassExtendsImplementsContainerTest.parser.readTopExpression
    assertNotNull(expression)
    assertSame(classOf[ClassContainer], expression getClass)
    val classContainer = expression.asInstanceOf[ClassContainer]
    assertEquals("Test", classContainer.name)
    assertEquals("ABC", classContainer.extendsClass)
    assertEquals(1, classContainer.implementsClass size)
    assertEquals("AAA", classContainer.implementsClass head)
    assertEquals(1, classContainer.variables size)
    assertEquals(1, classContainer.functions size)
  }
}