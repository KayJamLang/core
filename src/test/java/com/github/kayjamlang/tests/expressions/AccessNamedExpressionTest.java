package com.github.kayjamlang.tests.expressions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.containers.NamedExpression;
import com.github.kayjamlang.core.expressions.Access;
import com.github.kayjamlang.core.expressions.VariableLink;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class AccessNamedExpressionTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("test.test {}"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(Access.class, expression.getClass());

        Access access = (Access) expression;
        assertSame(VariableLink.class, access.root.getClass());
        assertSame(NamedExpression.class, access.child.getClass());

        NamedExpression namedContainer = (NamedExpression) access.child;
        assertSame(Container.class, namedContainer.expression.getClass());
    }
}