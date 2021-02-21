package com.github.kayjam.tests.expressions.callcreate;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;
import com.github.kayjam.core.expressions.ArrayGet;
import com.github.kayjam.core.expressions.CallCreate;
import com.github.kayjam.core.expressions.Const;
import com.github.kayjam.core.expressions.VariableLink;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CallCreateTwoArgsTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("concat(2021,\"year\")"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(CallCreate.class, expression.getClass());

        CallCreate callCreate = (CallCreate) expression;
        assertEquals("concat", callCreate.functionName);
        assertEquals(2, callCreate.arguments.size());

        Expression firstArgument = callCreate.arguments.get(0);
        assertSame(Const.class, firstArgument.getClass());

        Const firstArgumentConstant = (Const) firstArgument;
        assertEquals(2021, firstArgumentConstant.value);

        Expression secondaryArgument = callCreate.arguments.get(1);
        assertSame(Const.class, secondaryArgument.getClass());

        Const secondaryArgumentConstant = (Const) secondaryArgument;
        assertEquals("year", secondaryArgumentConstant.value);
    }
}