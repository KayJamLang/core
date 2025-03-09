package com.github.kayjamlang.backend.ts;

import com.github.kayjamlang.backend.tree.KayJamExpressionVisitor;
import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.FunctionContainer;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.expressions.data.Operation;
import com.github.kayjamlang.core.expressions.loops.ForExpression;
import com.github.kayjamlang.core.expressions.loops.WhileExpression;

import java.util.Map;

public class DTSKayJamExpressionVisitor extends KayJamExpressionVisitor<String> {
    private static String snakeToCamel(String str) {
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '_') {
                builder.deleteCharAt(i);
                builder.replace(i, i + 1, String.valueOf(Character.toUpperCase(builder.charAt(i))));
            }
        }

        return builder.toString();
    }

    private String normalizeNamespace(String namespace) {
        if (namespace.equals("\\")) {
            return "root";
        }

        return snakeToCamel(namespace.replace('\\', '_').replaceFirst("_", ""));
    }

    @Override
    public String visitKayJamFile(KayJamFile file) {
        String jsNamespace = normalizeNamespace(file.namespace);
        StringBuilder result = new StringBuilder("export const " + jsNamespace + ": {");
        for (Map.Entry<String, Expression> constant : file.constants.entrySet()) {
            result.append(constant.getKey()).append(":").append(visit(constant.getValue())).append(";");
        }

        for (Map.Entry<String, ClassContainer> entry : file.classes.entrySet()) {
            ClassContainer classContainer = entry.getValue();
            result.append(normalizeNamespace(classContainer.name))
                    .append(": {");

            if (classContainer instanceof ObjectContainer) {
                ObjectContainer objectContainer = (ObjectContainer) classContainer;
                for (Map.Entry<String, Expression> constant : objectContainer.constants.entrySet()) {
                    result.append(constant.getKey()).append(":").append(visit(constant.getValue())).append(";");
                }
            }

            result.append("};");
        }

        result.append("};");

        return result.toString();
    }

    @Override
    public String visitBitwiseComplementExpression(BitwiseComplementExpression expression) {
        return "number";
    }

    @Override
    public String visitAccessExpression(AccessExpression expression) {
        return visit(expression);
    }

    @Override
    public String visitArrayExpression(ArrayExpression expression) {
        return visit(expression) + "[]";
    }

    @Override
    public String visitAssertNullExpression(AssertNullExpression expression) {
        return visit(expression.expression);
    }

    @Override
    public String visitCallOrCreateExpression(CallOrCreateExpression expression) {
        return "any";
    }

    @Override
    public String visitCastExpression(CastExpression expression) {
        return expression.castType.name;
    }

    @Override
    public String visitCompanionAccessExpression(CompanionAccessExpression expression) {
        return "any";
    }

    @Override
    public String visitFunctionRefExpression(FunctionRefExpression expression) {
        return "";
    }

    @Override
    public String visitGetExpression(GetExpression expression) {
        return "any";
    }

    @Override
    public String visitIfExpression(IfExpression expression) {
        return "";
    }

    @Override
    public String visitIsExpression(IsExpression expression) {
        return "";
    }

    @Override
    public String visitNegationExpression(NegationExpression expression) {
        return "!" + expression.expression;
    }

    @Override
    public String visitOperationExpression(OperationExpression expression) {
        String operator = "";
        if (expression.operation == Operation.PLUS && (visit(expression.left).equals("string") || visit(expression.right).equals("string"))) {
            return "string";
        }

        switch (expression.operation) {
            case AND:
            case OR:
            case EQUALS:
            case NOT_EQUALS:
            case LESS_SIGN:
            case GREATER_SIGN:
            case LESS_EQUALS_SIGN:
            case GREATER_EQUALS_SIGN:
                return "boolean";
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case DIVISION:
            case BITWISE_OR:
            case BITWISE_AND:
            case BITWISE_SHIFT_LEFT:
            case BITWISE_SHIFT_RIGHT:
                return "number";
            case RANGE:
                return "number[]";
        }

        return visit(expression.left) + operator + visit(expression.right);
    }

    @Override
    public String visitReturnExpression(ReturnExpression expression) {
        return visit(expression.expression);
    }

    @Override
    public String visitValueExpression(ValueExpression expression) {
        if (expression.value instanceof String) {
            return "string";
        } else if (expression.value instanceof Number) {
            return "number";
        } else if (expression.value instanceof Boolean) {
            return "boolean";
        }

        return "null";
    }

    @Override
    public String visitVariableExpression(VariableExpression expression) {
        return "";
    }

    @Override
    public String visitVariableLinkExpression(VariableLinkExpression expression) {
        return expression.name;
    }

    @Override
    public String visitForExpression(ForExpression expression) {
        return "";
    }

    @Override
    public String visitWhileExpression(WhileExpression expression) {
        return "";
    }

    @Override
    public String visitFunctionContainer(FunctionContainer expression) {
        StringBuilder result = new StringBuilder("const " + expression.name + " = () => {");
        for (Expression child : expression.children) {
            result.append(visit(child)).append(";");
        }

        result.append("}");
        return result.toString();
    }
}
