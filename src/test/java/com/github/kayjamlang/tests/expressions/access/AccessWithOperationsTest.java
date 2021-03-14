package com.github.kayjamlang.tests.expressions.access;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.*;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccessWithOperationsTest {

    private static KayJamParser parser;

    @BeforeClass
    public static void prepare(){
        parser = new KayJamParser(new KayJamLexer("getRoot().child+123"));
    }

    @Test
    public void test() throws Exception {
        Expression expression = parser.readExpression();

        assertNotNull(expression);
        assertSame(OperationExpression.class, expression.getClass());

        OperationExpression operationExpression = (OperationExpression) expression;
        assertSame(Access.class, operationExpression.left.getClass());
        assertSame(Const.class, operationExpression.right.getClass());

        Access access = (Access) operationExpression.left;
        assertSame(CallCreate.class, access.root.getClass());
        assertSame(VariableLink.class, access.child.getClass());

        CallCreate root = (CallCreate) access.root;
        assertEquals("getRoot", root.functionName);
        assertEquals(0, root.arguments.size());

        VariableLink child = (VariableLink) access.child;
        assertEquals("child", child.name);
    }
}