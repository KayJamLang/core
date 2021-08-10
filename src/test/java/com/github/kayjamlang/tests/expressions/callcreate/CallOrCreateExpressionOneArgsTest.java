package com.github.kayjamlang.tests.expressions.callcreate;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.CallOrCreateExpression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CallOrCreateExpressionOneArgsTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("concat(2021)"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(CallOrCreateExpression.class, expression.getClass());

        CallOrCreateExpression callOrCreateExpression = (CallOrCreateExpression) expression;
        assertEquals("concat", callOrCreateExpression.name);
        assertEquals(1, callOrCreateExpression.arguments.size());

        Expression firstArgument = callOrCreateExpression.arguments.get(0);
        assertSame(ValueExpression.class, firstArgument.getClass());

        ValueExpression firstArgumentConstant = (ValueExpression) firstArgument;
        assertEquals(2021, firstArgumentConstant.value);
    }
}