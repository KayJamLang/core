package com.github.kayjamlang.tests.expressions.ifExpr;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.GetExpression;
import com.github.kayjamlang.core.expressions.IfExpression;
import com.github.kayjamlang.core.expressions.OperationExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class IfWithGetOperationTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("if(test[1]==123) true"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(IfExpression.class, expression.getClass());

        IfExpression ifExpression = (IfExpression) expression;
        assertSame(OperationExpression.class, ifExpression.condition.getClass());

        assertNotNull(ifExpression.ifTrue);
        assertNull(ifExpression.ifFalse);

        assertSame(ValueExpression.class, ifExpression.ifTrue.getClass());

        OperationExpression root = (OperationExpression) ifExpression.condition;
        assertSame(GetExpression.class, root.left.getClass());
        assertSame(ValueExpression.class, root.right.getClass());

        ValueExpression ifTrue = (ValueExpression) ifExpression.ifTrue;
        assertEquals(true, ifTrue.value);
    }
}