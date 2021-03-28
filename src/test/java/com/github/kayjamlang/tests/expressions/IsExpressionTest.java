package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.CastExpression;
import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.expressions.IsExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IsExpressionTest {
    private static KayJamParser parser;

    @BeforeClass
    public static void prepare() {
        parser = new KayJamParser(new KayJamLexer("123 is int"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();
        assertNotNull(expression);
        assertEquals(IsExpression.class, expression.getClass());

        IsExpression isExpression = (IsExpression) expression;
        assertEquals(Const.class, isExpression.expression.getClass());
        assertEquals(Type.INTEGER, isExpression.verifyType);
    }
}
