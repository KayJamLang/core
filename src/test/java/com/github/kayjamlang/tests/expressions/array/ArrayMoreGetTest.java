package com.github.kayjamlang.tests.expressions.array;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.expressions.GetExpression;
import com.github.kayjamlang.core.expressions.VariableLink;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayMoreGetTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("test[2005][2006]"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(GetExpression.class, expression.getClass());

        GetExpression arrayGet = (GetExpression) expression;
        assertSame(GetExpression.class, arrayGet.root.getClass());
        assertSame(Const.class, arrayGet.value.getClass());

        GetExpression getExpression = (GetExpression) arrayGet.root;
        assertSame(VariableLink.class, getExpression.root.getClass());
        assertSame(Const.class, getExpression.value.getClass());

        Const constant = (Const) arrayGet.value;
        assertEquals(2006L, constant.value);
    }
}