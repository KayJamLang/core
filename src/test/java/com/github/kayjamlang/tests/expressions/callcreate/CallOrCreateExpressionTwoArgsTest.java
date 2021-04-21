package com.github.kayjamlang.tests.expressions.callcreate;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.CallOrCreateExpression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CallOrCreateExpressionTwoArgsTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("concat(2021,\"year\")"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(CallOrCreateExpression.class, expression.getClass());

        CallOrCreateExpression callOrCreateExpression = (CallOrCreateExpression) expression;
        assertEquals("concat", callOrCreateExpression.functionName);
        assertEquals(2, callOrCreateExpression.arguments.size());

        Expression firstArgument = callOrCreateExpression.arguments.get(0);
        assertSame(ValueExpression.class, firstArgument.getClass());

        ValueExpression firstArgumentConstant = (ValueExpression) firstArgument;
        assertEquals(2021, firstArgumentConstant.value);

        Expression secondaryArgument = callOrCreateExpression.arguments.get(1);
        assertSame(ValueExpression.class, secondaryArgument.getClass());

        ValueExpression secondaryArgumentConstant = (ValueExpression) secondaryArgument;
        assertEquals("year", secondaryArgumentConstant.value);
    }
}