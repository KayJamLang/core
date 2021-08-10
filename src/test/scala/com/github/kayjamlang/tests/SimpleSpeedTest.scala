package com.github.kayjamlang.tests

import com.github.kayjamlang.core.{KayJamLexer, KayJamParser}
import org.apache.commons.lang3.time.StopWatch

object SimpleSpeedTest {
  def main(args: Array[String]): Unit = {
    val sw = new StopWatch()
    sw.start()
    new KayJamParser(new KayJamLexer(
      """
        |class A {
        | println("Hello, World!")
        |}
        |""".stripMargin)).parseStmt()
    sw.stop()
    println(sw)
  }
}
