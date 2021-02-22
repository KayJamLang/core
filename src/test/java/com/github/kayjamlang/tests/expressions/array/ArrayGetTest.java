package com.github.kayjamlang.tests.expressions.array;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ArrayGet;
import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.expressions.VariableLink;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayGetTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("test[2005]"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(ArrayGet.class, expression.getClass());

        ArrayGet arrayGet = (ArrayGet) expression;
        assertSame(VariableLink.class, arrayGet.root.getClass());
        assertSame(Const.class, arrayGet.value.getClass());

        VariableLink variableLink = (VariableLink) arrayGet.root;
        assertEquals("test", variableLink.name);

        Const constant = (Const) arrayGet.value;
        assertEquals(2005L, constant.value);
    }
}