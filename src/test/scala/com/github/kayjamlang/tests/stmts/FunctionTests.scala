package com.github.kayjamlang.tests.stmts

import com.github.kayjamlang.core.Type
import com.github.kayjamlang.core.containers.Container
import com.github.kayjamlang.core.stmts.FunctionStmt
import com.github.kayjamlang.tests.TestsUtils
import org.junit.Assert._
import org.junit.Test

class FunctionTests {

  @Test
  def parseFunctionWithContainer(): Unit = {
    val stmt = TestsUtils.parseStmt(
      """
        |fun test(string arg): void {
        | var test = 123;
        |}
        |""".stripMargin)
    assertEquals(classOf[FunctionStmt], stmt.getClass)

    val function = stmt.asInstanceOf[FunctionStmt]
    assertEquals(classOf[Container], function.body.getClass)
    assertEquals("test", function.name)
    assertEquals(Type.VOID, function.returnType)
    assertEquals(1, function.arguments.length)
    assertEquals("test(string;)void", function.desc)
  }
}
