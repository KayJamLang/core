package com.github.kayjamlang.tests.statements;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.statements.FunctionStatement;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.statements.Statement;
import com.github.kayjamlang.tests.TestsUtils;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class FunctionStatementTest {
    @Test
    public void functionWithOneArgument() throws Exception {
        Statement expression = TestsUtils.parseStatement("fun test(string value){true;false}");

        assertNotNull(expression);
        assertSame(FunctionStatement.class, expression.getClass());

        FunctionStatement functionStatement = (FunctionStatement) expression;
        assertEquals(1, functionStatement.arguments.size());
        assertEquals(2, functionStatement.children.size());

        Argument argument = functionStatement.arguments.get(0);
        assertEquals(Type.STRING, argument.type);
        assertEquals("value", argument.name);
    }

    @Test
    public void functionWithMultipleArgument() throws Exception {
        Statement expression = TestsUtils.parseStatement("fun test(string value, int integer){true;false}");

        assertNotNull(expression);
        assertSame(FunctionStatement.class, expression.getClass());

        FunctionStatement functionStatement = (FunctionStatement) expression;
        assertEquals(2, functionStatement.arguments.size());
        assertEquals(2, functionStatement.children.size());

        Argument argument = functionStatement.arguments.get(0);
        assertEquals(Type.STRING, argument.type);
        assertEquals("value", argument.name);

        argument = functionStatement.arguments.get(1);
        assertEquals(Type.INTEGER, argument.type);
        assertEquals("integer", argument.name);
    }

    @Test
    public void functionWithoutArguments() throws Exception {
        Statement expression = TestsUtils.parseStatement("fun test(){true;false}");

        assertNotNull(expression);
        assertSame(FunctionStatement.class, expression.getClass());

        FunctionStatement functionStatement = (FunctionStatement) expression;
        assertEquals(0, functionStatement.arguments.size());
        assertEquals(2, functionStatement.children.size());
    }

    @Test
    public void functionWithReturnType() throws Exception {
        Statement expression = TestsUtils.parseStatement("fun test(): string {true;false}");

        assertNotNull(expression);
        assertSame(FunctionStatement.class, expression.getClass());

        FunctionStatement functionStatement = (FunctionStatement) expression;
        assertEquals(0, functionStatement.arguments.size());
        assertEquals(2, functionStatement.children.size());
        assertEquals(Type.STRING, functionStatement.returnType);
    }
}
