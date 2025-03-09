package com.github.kayjamlang.backend.ts;

import com.github.kayjamlang.backend.tree.KayJamExpressionVisitor;
import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.FunctionContainer;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.expressions.loops.ForExpression;
import com.github.kayjamlang.core.expressions.loops.WhileExpression;

import java.util.Map;

public class JSKayJamExpressionVisitor extends KayJamExpressionVisitor<String> {
    private static String snakeToCamel(String str) {
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '_') {
                builder.deleteCharAt(i);
                builder.replace(
                        i, i + 1,
                        String.valueOf(
                                Character.toUpperCase(
                                        builder.charAt(i))));
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
        StringBuilder result = new StringBuilder("var " + jsNamespace + "=" + jsNamespace + "||{};");
        for (Map.Entry<String, Expression> constant : file.constants.entrySet()) {
            result.append(jsNamespace)
                    .append(".")
                    .append(constant.getKey())
                    .append("=")
                    .append(visit(constant.getValue()))
                    .append(";");
        }

        result.append("(()=>{");
//        for (KayJamFile.Usage usage : file.usages) {
//            result.append("const {");
//            for (String required : usage.required) {
//                result.append(normalizeNamespace(required))
//                        .append(",");
//            }
//
//            result.append("}=").append().append(";");
//        }

        for (Map.Entry<String, ClassContainer> entry : file.classes.entrySet()) {
            ClassContainer classContainer = entry.getValue();
            result.append(jsNamespace)
                    .append(".")
                    .append(normalizeNamespace(classContainer.name))
                    .append("=function ")
                    .append(normalizeNamespace(entry.getKey()));

            result.append("(");

            result.append(")");

            result.append("{");

            result.append("};");

            if(classContainer instanceof ObjectContainer) {
                ObjectContainer objectContainer = (ObjectContainer) classContainer;
                for (Map.Entry<String, Expression> constant: objectContainer.constants.entrySet()){
                    result.append(jsNamespace)
                            .append(".")
                            .append(normalizeNamespace(classContainer.name))
                            .append(".")
                            .append(constant.getKey())
                            .append("=")
                            .append(visit(constant.getValue()))
                            .append(";");
                }
            }
        }

        if (!file.constants.isEmpty()) {
            result.append("const {");
            for (Map.Entry<String, Expression> constant : file.constants.entrySet()) {
                result.append(constant.getKey())
                        .append(",");
            }
            result.append("}=").append(jsNamespace).append(";");
        }


        for (Expression expression : file.children) {
            result.append(visit(expression)).append(";");
        }
        result.append("})();");
        result.append("export {").append(jsNamespace).append("};");


        return result.toString();
    }

    @Override
    public String visitBitwiseComplementExpression(BitwiseComplementExpression expression) {
        return "~" + visit(expression);
    }

    @Override
    public String visitAccessExpression(AccessExpression expression) {
        return visit(expression.root) + "." + visit(expression.child);
    }

    @Override
    public String visitArrayExpression(ArrayExpression expression) {
        return "";
    }

    @Override
    public String visitAssertNullExpression(AssertNullExpression expression) {
        return "(" + visit(expression.expression) + ") ?? (() => { throw new Error(\"" + expression.expression + " is null\"); })()";
    }

    @Override
    public String visitCallOrCreateExpression(CallOrCreateExpression expression) {
        StringBuilder arguments = new StringBuilder();
        for (int i = 0; i < expression.arguments.size(); i++) {
            arguments.append(visit(expression.arguments.get(i)));
            if (i != expression.arguments.size() - 1) {
                arguments.append(",");
            }
        }

        return expression.name + "(" + arguments + ")";
    }

    @Override
    public String visitCastExpression(CastExpression expression) {
        return visit(expression.expression);
    }

    @Override
    public String visitCompanionAccessExpression(CompanionAccessExpression expression) {
        return expression.className + "." + visit(expression.child);
    }

    @Override
    public String visitFunctionRefExpression(FunctionRefExpression expression) {
        return "";
    }

    @Override
    public String visitGetExpression(GetExpression expression) {
        return visit(expression.root) + "[" + visit(expression.value) + "]";
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
        switch (expression.operation) {
            case AND:
                operator = "&&";
                break;
            case OR:
                operator = "||";
                break;
            case PLUS:
                operator = "+";
                break;
            case MINUS:
                operator = "-";
                break;
            case MULTIPLY:
                operator = "*";
                break;
            case RANGE:
                return "(() => {var a=" + visit(expression.left) + ",b=" + visit(expression.right) + "+1;return Array.from({length: b-a},(_, i)=>i+a);})()";
            case EQUALS:
                operator = "==";
                break;
            case NOT_EQUALS:
                operator = "!=";
                break;
            case DIVISION:
                operator = "/";
                break;
            case LESS_SIGN:
                operator = "<";
                break;
            case GREATER_SIGN:
                operator = ">";
                break;
            case BITWISE_OR:
                operator = "|";
                break;
            case BITWISE_AND:
                operator = "&";
                break;
            case LESS_EQUALS_SIGN:
                operator = "<=";
                break;
            case GREATER_EQUALS_SIGN:
                operator = ">=";
                break;
            case BITWISE_SHIFT_LEFT:
                operator = "<<";
                break;
            case BITWISE_SHIFT_RIGHT:
                operator = ">>";
                break;
        }

        return visit(expression.left) + operator + visit(expression.right);
    }

    @Override
    public String visitReturnExpression(ReturnExpression expression) {
        return "";
    }

    @Override
    public String visitValueExpression(ValueExpression expression) {
        if (expression.value instanceof String) {
            return "\"" + expression.value + "\"";
        } else if (expression.value instanceof Number) {
            return String.valueOf(expression.value);
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
