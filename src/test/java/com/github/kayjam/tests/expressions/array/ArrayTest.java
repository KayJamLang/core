package com.github.kayjam.tests.expressions.array;

import com.github.kayjam.core.Expression;
import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;
import com.github.kayjam.core.expressions.Array;
import com.github.kayjam.core.expressions.Const;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;

public class ArrayTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("[1,2,3,4,5]"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(Array.class, expression.getClass());

        Array array = (Array) expression;
        assertEquals(5, array.values.size());

        for (int i = 0; i < array.values.size(); i++) {
            Expression value = array.values.get(i);
            assertSame(Const.class, value.getClass());

            Const constantValue = (Const) value;
            assertEquals(i+1, constantValue.value);
        }
    }
}