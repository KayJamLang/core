package com.github.kayjamlang.tests.expressions.ifExpr;

import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class IfExpressionTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("if(putin) true else false");

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

    @Test
    public void withOperations() throws Exception {
        Expression expression = TestsUtils.parse("if(test[1]==123) true");

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