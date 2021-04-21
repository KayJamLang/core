package com.github.kayjamlang.tests.expressions.ifExpr;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.IfExpression;
import com.github.kayjamlang.core.expressions.VariableLinkExpression;
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
        assertSame(IfExpression.class, expression.getClass());

        IfExpression ifExpression = (IfExpression) expression;
        assertSame(VariableLinkExpression.class, ifExpression.condition.getClass());

        assertNotNull(ifExpression.ifTrue);
        assertNotNull(ifExpression.ifFalse);

        assertSame(ValueExpression.class, ifExpression.ifTrue.getClass());
        assertSame(ValueExpression.class, ifExpression.ifFalse.getClass());

        VariableLinkExpression root = (VariableLinkExpression) ifExpression.condition;
        assertEquals("putin", root.name);

        ValueExpression ifTrue = (ValueExpression) ifExpression.ifTrue;
        assertEquals(true, ifTrue.value);

        ValueExpression ifFalse = (ValueExpression) ifExpression.ifFalse;
        assertEquals(false, ifFalse.value);
    }
}