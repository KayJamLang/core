package com.github.kayjamlang.tests.expressions.access;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.expressions.*;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccessExpressionWithOperationsTest {

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
        assertSame(AccessExpression.class, operationExpression.left.getClass());
        assertSame(ValueExpression.class, operationExpression.right.getClass());

        AccessExpression accessExpression = (AccessExpression) operationExpression.left;
        assertSame(CallOrCreateExpression.class, accessExpression.root.getClass());
        assertSame(VariableLinkExpression.class, accessExpression.child.getClass());

        CallOrCreateExpression root = (CallOrCreateExpression) accessExpression.root;
        assertEquals("getRoot", root.functionName);
        assertEquals(0, root.arguments.size());

        VariableLinkExpression child = (VariableLinkExpression) accessExpression.child;
        assertEquals("child", child.name);
    }
}