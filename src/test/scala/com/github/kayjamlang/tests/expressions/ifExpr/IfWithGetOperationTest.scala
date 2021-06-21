package com.github.kayjamlang.tests.expressions.ifExpr

import com.github.kayjamlang.core.KayJamLexer
import com.github.kayjamlang.core.KayJamParser
import com.github.kayjamlang.core.expressions._
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert._


object IfWithGetOperationTest {
  private var parser: KayJamParser = null

  @BeforeClass def prepare(): Unit = {
    parser = new KayJamParser(new KayJamLexer(
      """
        |if (test[1] == 123)
        | true
        |""".stripMargin))
  }
}

class IfWithGetOperationTest {
  @Test
  @throws[Exception]
  def test(): Unit = {
    val expression = IfWithGetOperationTest.parser.readExpression
    assertNotNull(expression)
    assertSame(classOf[IfExpression], expression getClass)
    val ifExpression = expression.asInstanceOf[IfExpression]
    assertSame(classOf[OperationExpression], ifExpression.condition getClass)
    assertNotNull(ifExpression.ifTrue)
    assertNull(ifExpression.ifFalse)
    assertSame(classOf[ValueExpression], ifExpression.ifTrue getClass)
    val root = ifExpression.condition.asInstanceOf[OperationExpression]
    assertSame(classOf[GetExpression], root.left getClass)
    assertSame(classOf[ValueExpression], root.right getClass)
    val ifTrue = ifExpression.ifTrue.asInstanceOf[ValueExpression]
    assertEquals(true, ifTrue.value)
  }
}