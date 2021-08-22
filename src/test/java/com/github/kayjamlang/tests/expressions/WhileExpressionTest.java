package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.loops.WhileExpression;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class WhileExpressionTest {

    @Test
    public void test() throws Exception {
        Expression expression = TestsUtils.parse("while(false) true");

        assertNotNull(expression);
        assertSame(WhileExpression.class, expression.getClass());

        WhileExpression whileExpression = (WhileExpression) expression;
        assertSame(ValueExpression.class, whileExpression.condition.getClass());
        assertSame(ValueExpression.class, whileExpression.expression.getClass());

        ValueExpression condition = (ValueExpression) whileExpression.condition;
        assertEquals(false, condition.value);

        ValueExpression cValue = (ValueExpression) whileExpression.expression;
        assertEquals(true, cValue.value);
    }
}