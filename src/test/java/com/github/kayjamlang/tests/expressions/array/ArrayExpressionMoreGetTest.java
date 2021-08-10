package com.github.kayjamlang.tests.expressions.array;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.expressions.GetExpression;
import com.github.kayjamlang.core.expressions.VariableLinkExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayExpressionMoreGetTest {

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
        assertSame(ValueExpression.class, arrayGet.value.getClass());

        GetExpression getExpression = (GetExpression) arrayGet.root;
        assertSame(VariableLinkExpression.class, getExpression.root.getClass());
        assertSame(ValueExpression.class, getExpression.value.getClass());

        ValueExpression constant = (ValueExpression) arrayGet.value;
        assertEquals(2006, constant.value);
    }
}