package com.github.kayjamlang.tests

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser

object Test {
  @throws[Exception]
  def main(args: Array[String]): Unit = {
    val parser = new KayJamParser(new KayJamLexer("{" + "if(postData[1]!=secretKey){\n" + "\t\tprint(\"Invalid secret key\");\n" + "\t\treturn;\n" + "\t}" + "}"))
    parser.readExpression
  }
}
