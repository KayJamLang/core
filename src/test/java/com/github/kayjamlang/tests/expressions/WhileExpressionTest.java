package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.expressions.Return;
import com.github.kayjamlang.core.expressions.WhileExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class WhileExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("while(false) true"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(WhileExpression.class, expression.getClass());

        WhileExpression whileExpression = (WhileExpression) expression;
        assertSame(Const.class, whileExpression.condition.getClass());
        assertSame(Const.class, whileExpression.expression.getClass());

        Const condition = (Const) whileExpression.condition;
        assertEquals(false, condition.value);

        Const cValue = (Const) whileExpression.expression;
        assertEquals(true, cValue.value);
    }
}