package com.github.kayjamlang.tests.expressions.ifExpr;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.expressions.If;
import com.github.kayjamlang.core.expressions.VariableLink;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class IfWithExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("if(putin) true else false"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(If.class, expression.getClass());

        If ifExpression = (If) expression;
        assertSame(VariableLink.class, ifExpression.condition.getClass());

        assertNotNull(ifExpression.ifTrue);
        assertNotNull(ifExpression.ifFalse);

        assertSame(Const.class, ifExpression.ifTrue.getClass());
        assertSame(Const.class, ifExpression.ifFalse.getClass());

        VariableLink root = (VariableLink) ifExpression.condition;
        assertEquals("putin", root.name);

        Const ifTrue = (Const) ifExpression.ifTrue;
        assertEquals(true, ifTrue.value);

        Const ifFalse = (Const) ifExpression.ifFalse;
        assertEquals(false, ifFalse.value);
    }
}