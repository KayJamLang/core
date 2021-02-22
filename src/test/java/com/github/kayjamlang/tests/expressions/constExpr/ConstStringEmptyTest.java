package com.github.kayjamlang.tests.expressions.constExpr;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.Const;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConstStringEmptyTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("\"\""));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(Const.class, expression.getClass());

        Const constant = (Const) expression;
        assertEquals("", constant.value);
    }
}