package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.expressions.NamedExpression;
import com.github.kayjamlang.core.expressions.AccessExpression;
import com.github.kayjamlang.core.expressions.VariableLinkExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class AccessExpressionNamedExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("test.test {}"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(AccessExpression.class, expression.getClass());

        AccessExpression accessExpression = (AccessExpression) expression;
        assertSame(VariableLinkExpression.class, accessExpression.root.getClass());
        assertSame(NamedExpression.class, accessExpression.child.getClass());

        NamedExpression namedContainer = (NamedExpression) accessExpression.child;
        assertSame(Container.class, namedContainer.stmt.getClass());
    }
}