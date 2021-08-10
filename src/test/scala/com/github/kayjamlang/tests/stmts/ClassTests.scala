package com.github.kayjamlang.tests.stmts

import com.github.kayjamlang.tests.TestsUtils
import org.junit.Test

class ClassTests {

  @Test
  def test1(): Unit = { // TODO: please rename test
    val stmt = TestsUtils.parseStmt(
      """
        |class A {
        | var i = 12;
        |}
        |""".stripMargin)
  }
}
