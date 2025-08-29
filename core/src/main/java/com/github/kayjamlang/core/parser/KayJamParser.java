package com.github.kayjamlang.core.parser;

import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.Token;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.expressions.data.Annotation;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.expressions.loops.ForExpression;
import com.github.kayjamlang.core.expressions.data.Operation;
import com.github.kayjamlang.core.expressions.loops.WhileExpression;
import com.github.kayjamlang.core.opcodes.AccessType;
import com.github.kayjamlang.core.parser.data.ConstantParser;
import com.github.kayjamlang.core.parser.statement.ClassStatementParser;
import com.github.kayjamlang.core.parser.statement.FunctionStatementParser;
import com.github.kayjamlang.core.parser.statement.ObjectStatementParser;
import com.github.kayjamlang.core.parser.statement.TypeAliasStatementParser;
import com.github.kayjamlang.core.statements.Statement;

import java.util.*;

public class KayJamParser {
    public final KayJamFile file;
    public final KayJamLexer lexer;
    private static final Map<String, Integer> binOperationPrecedence;

    static {
        binOperationPrecedence = new HashMap<>();
        binOperationPrecedence.put("as", 0);
        binOperationPrecedence.put("is", 0);
        binOperationPrecedence.put("<", 10);
        binOperationPrecedence.put("=<", 10);
        binOperationPrecedence.put(">", 10);
        binOperationPrecedence.put(">=", 10);
        binOperationPrecedence.put("+", 20);
        binOperationPrecedence.put("-", 20);
        binOperationPrecedence.put("==", 20);
        binOperationPrecedence.put("!=", 20);
        binOperationPrecedence.put("*", 40);
        binOperationPrecedence.put("/", 40);
        binOperationPrecedence.put("..", 50);
        binOperationPrecedence.put(".", 50);
        binOperationPrecedence.put("&", 50);
        binOperationPrecedence.put("|", 50);
        binOperationPrecedence.put("<<", 50);
        binOperationPrecedence.put(">>", 50);
        binOperationPrecedence.put("^", 50);
        binOperationPrecedence.put("&&", 70);
        binOperationPrecedence.put("||", 70);
    }

    public KayJamParser(KayJamFile file, KayJamLexer lexer) {
        this.file = file;
        this.lexer = lexer;
        this.lexer.source = file;
    }

    public Expression readExpression() throws KayJamLexerException, KayJamParserException {
        Expression expression = readPrimary();
        switch (currentTokenType()) {
            case EOF, Token.Type.CLOSE_BRACKET -> {
                return expression;
            }

        }

        moveAhead();
        expression = readEndExpression(expression);
        expression = parseBinOpRHS(0, expression);

        moveAhead();
        while (currentTokenType() == Token.Type.TK_OPEN_SQUARE_BRACKET) {
            int line = lexer.getLine();

            moveAhead();
            expression = new GetExpression(expression, readExpression(), line);
            requireToken(Token.Type.TK_CLOSE_SQUARE_BRACKET);

            moveAhead();
            expression = readEndExpression(expression);
        }

        expression = parseBinOpRHS(0, expression);
        return expression;
    }

    public Expression readEndExpression(Expression root) throws KayJamLexerException {
        return switch (currentTokenType()) {
            case TK_NOT -> {
                moveAhead();
                yield new AssertNullExpression(root, lexer.getLine());
            }
            case TK_PLUS_ONE -> {
                moveAhead();
                yield new OperationExpression(root,
                        new ValueExpression(1), Operation.PLUS, lexer.getLine());
            }
            case TK_MINUS_ONE -> {
                moveAhead();
                yield new OperationExpression(root,
                        new ValueExpression(1), Operation.MINUS, lexer.getLine());
            }
            default -> root;
        };
    }

    public Token.Type currentTokenType() {
        return lexer.currentToken().type;
    }

    public Token moveAhead() throws KayJamLexerException {
        lexer.moveAhead();
        if (!lexer.isSuccessful()) {
            throw new KayJamLexerException(lexer, lexer.errorMessage());
        }

        return lexer.currentToken();
    }

    public Token requireToken(Token.Type type) throws KayJamLexerException, KayJamParserException {
        return requireToken(type, true);
    }

    public Token requireToken(Token.Type type, boolean moveAhead) throws KayJamLexerException, KayJamParserException {
        Token token = moveAhead ? moveAhead(): lexer.currentToken();
        if (token.type != type)
            throw new KayJamParserException(lexer, "expected " + type.name().toLowerCase() + ", not " + token.value);

        return lexer.currentToken();
    }


    public void requireIdentifier(KayJamParserKeywords identifier) throws KayJamLexerException, KayJamParserException {
        Token token = requireToken(Token.Type.IDENTIFIER);
        if (KayJamParserKeywords.find(token.value) != identifier)
            throw new KayJamParserException(lexer, "expected " + identifier.name());

    }

    public Expression readPrimary() throws KayJamLexerException,
            KayJamParserException {
        Token.Type type = currentTokenType();
        int line = lexer.getLine();
        switch (type) {
            case IDENTIFIER: {
                KayJamParserKeywords keyword = KayJamParserKeywords.find(lexer.currentToken().value);
                if (keyword != null) {
                    switch (keyword) {
                        case VAR: {
                            String name = requireToken(Token.Type.IDENTIFIER).value;
                            requireToken(Token.Type.TK_ASSIGN);

                            moveAhead();
                            Expression expression = readExpression();

                            return new VariableExpression(name, expression, line);
                        }
                        case PRIVATE:
                            moveAhead();
                            return readExpression();
                        case PUBLIC:
                            moveAhead();
                            return readExpression();
                        case WHILE: {
                            requireToken(Token.Type.TK_OPEN);

                            moveAhead();
                            Expression condition = readExpression();

                            if (currentTokenType() != Token.Type.TK_CLOSE)
                                throw new KayJamParserException(lexer, "expected close \")\"");
                            else moveAhead();

                            moveAhead();
                            return new WhileExpression(condition, readExpression(), line);
                        }
                        case FOR: {
                            requireToken(Token.Type.TK_OPEN);

                            String name = requireToken(Token.Type.IDENTIFIER).value;
                            requireIdentifier(KayJamParserKeywords.IN);

                            moveAhead();
                            Expression range = readExpression();

                            if (currentTokenType() != Token.Type.TK_CLOSE)
                                throw new KayJamParserException(lexer, "expected close \")\"");
                            else moveAhead();

                            moveAhead();
                            return new ForExpression(name, range, readExpression(), line);
                        }
                        case RETURN:
                            if (moveAhead().value.equals("void"))
                                return new ReturnExpression(null, line);
                            return new ReturnExpression(readExpression(), line);
                        case IF: {
                            requireToken(Token.Type.TK_OPEN);

                            moveAhead();
                            Expression condition = readExpression();

                            requireToken(Token.Type.TK_CLOSE);

                            moveAhead();
                            Expression ifTrue = readExpression();
                            Expression ifFalse = null;

                            if (KayJamParserKeywords.find(moveAhead().value) == KayJamParserKeywords.ELSE) {
                                moveAhead();
                                ifFalse = readExpression();
                            } else {
                                lexer.input = new StringBuilder("}" + lexer.currentToken().value + lexer.input);
                                moveAhead();
                            }

                            return new IfExpression(condition, ifTrue, ifFalse, line);
                        }

                        default:
                            throw new KayJamParserException(lexer, "\"" + lexer.currentToken().value + "\" is in the wrong place");
                    }
                } else {
                    String name = lexer.currentToken().value;

                    type = moveAhead().type;
                    switch (type) {
                        case TK_ASSIGN: {
                            moveAhead();
                            Expression expression = readExpression();
                            return new VariableSetExpression(name, expression, line);
                        }
                        case TK_OPEN: {
                            List<Expression> arguments = new ArrayList<>();
                            while (moveAhead().type != Token.Type.TK_CLOSE) {
                                arguments.add(readExpression());

                                Token token = moveAhead();
                                if (token.type == Token.Type.TK_CLOSE)
                                    break;
                                else if (token.type != Token.Type.TK_COMMA)
                                    throw new KayJamParserException(lexer, "expected comma \",\"");
                            }

                            return new CallOrCreateExpression(name, arguments, line);
                        }
                        case TK_ACCESS:
                            moveAhead();
                            return new AccessExpression(new VariableLinkExpression(name, line), readExpression(), line);
                        case TK_COMPANION_ACCESS:
                            moveAhead();
                            return new CompanionAccessExpression(name, readExpression(), line);
                        case OPEN_BRACKET:
                        case TK_REF:
                            if (type == Token.Type.TK_REF)
                                moveAhead();
                            return new CallOrCreateExpression(name, List.of(
                                    new FunctionRefExpression(List.of(), readExpression(), Type.ANY, line)
                            ), line);
                        default:
                            lexer.input = new StringBuilder(lexer.currentToken().value + lexer.input);
                            return new VariableLinkExpression(name, line);
                    }
                }
            }
            case TK_OPEN: {
                moveAhead();

                Expression expression = readExpression();
                requireToken(Token.Type.TK_CLOSE);

                return expression;
            }
            case TK_REF: {
                List<Argument> arguments = new ArrayList<>();
                if (moveAhead().type == Token.Type.TK_OPEN) {
                    arguments = parseArguments();
                    moveAhead();
                }

                Type returnType = Type.VOID;
                if (moveAhead().type == Token.Type.TK_COLON) {
                    requireToken(Token.Type.IDENTIFIER);

                    returnType = parseType(true);
                }

                return new FunctionRefExpression(arguments, readExpression(), returnType, line);
            }
            case TK_NOT:
                moveAhead();
                return new NegationExpression(readExpression(), line);
            case TK_BITWISE_COMPLEMENT:
                moveAhead();
                return new BitwiseComplementExpression(readExpression(), line);
            case OPEN_BRACKET:
                return new ContainerExpression(parseStatements(), line);
            case TK_OPEN_SQUARE_BRACKET: {
                List<Expression> values = new ArrayList<>();
                while (moveAhead().type != Token.Type.TK_CLOSE_SQUARE_BRACKET) {
                    values.add(readExpression());

                    Token token = moveAhead();
                    if (token.type == Token.Type.TK_CLOSE_SQUARE_BRACKET)
                        break;
                    else if (token.type != Token.Type.TK_COMMA)
                        throw new KayJamParserException(line, "expected comma");
                }

                return new ArrayExpression(values, line);
            }

            default:
                Expression expression = parseValue();
                if (expression != null) return expression;
                throw new KayJamParserException(lexer, "\"" + lexer.currentToken().value + "\" is in the wrong place");
        }
    }

    public AccessType readAccessType() throws KayJamLexerException {
        KayJamParserKeywords keyword = KayJamParserKeywords.find(lexer.currentToken().value);
        return switch (keyword) {
            case PRIVATE -> {
                moveAhead();
                yield AccessType.PRIVATE;
            }
            case PUBLIC -> {
                moveAhead();
                yield AccessType.PUBLIC;
            }
            case PROTECTED -> {
                moveAhead();
                yield AccessType.PROTECTED;
            }
            case INTERNAL -> {
                moveAhead();
                yield AccessType.INTERNAL;
            }
            case null, default -> AccessType.NONE;
        };
    }

    public List<Annotation> readAnnotations() throws KayJamLexerException, KayJamParserException {
        List<Annotation> annotations = new ArrayList<>();
        while (currentTokenType() == Token.Type.TK_ANNOTATION) {
            int line = lexer.getLine();
            String name = requireToken(Token.Type.IDENTIFIER).value;
            moveAhead();

            if (currentTokenType() == Token.Type.TK_OPEN) {
                moveAhead();
                Expression value = readExpression();
                if (!(value instanceof ValueExpression))
                    throw new KayJamParserException(lexer, "Invalid value of annotation");

                annotations.add(new Annotation(name, (ValueExpression) value, line));
                requireToken(Token.Type.TK_CLOSE);

                moveAhead();
            } else annotations.add(new Annotation(name, line));
        }

        return annotations;
    }

    public Statement readStatement() throws KayJamParserException, KayJamLexerException {
        int line = lexer.getLine();
        switch (KayJamParserKeywords.find(lexer.currentToken().value)) {
            case FUNCTION:
                return FunctionStatementParser.parse(
                        List.of(),
                        AccessType.NONE,
                        this
                );

            case null, default:
                break;
        }

        return readExpression();
    }

    public Expression parseValue() throws KayJamLexerException {
        if (currentTokenType() == Token.Type.TK_MINUS) {
            moveAhead();

            return new OperationExpression(new ValueExpression(-1), parseValue(),
                    Operation.MULTIPLY, lexer.getLine());
        }

        return switch (currentTokenType()) {
            case STRING ->
                    new ValueExpression(lexer.currentToken().value.substring(1, lexer.currentToken().value.length() - 1));
            case NULL -> new ValueExpression(null);
            case LONG -> new ValueExpression(Long.parseLong(lexer.currentToken()
                    .value.substring(0, lexer.currentToken().value.length() - 1)));
            case INTEGER -> new ValueExpression(Integer.parseInt(lexer.currentToken().value));
            case REAL -> new ValueExpression(Double.parseDouble(lexer.currentToken().value));
            case BOOL -> new ValueExpression(lexer.currentToken().value.charAt(0) == 't');
            case null, default -> null;
        };
    }

    public String parseName() throws KayJamLexerException, KayJamParserException {
        if (currentTokenType() == Token.Type.TK_NAMESPACE_DELIMITER)
            moveAhead();

        if (currentTokenType() != Token.Type.IDENTIFIER)
            throw new KayJamParserException(lexer, "excepted type");

        StringBuilder name = new StringBuilder("\\" + lexer.currentToken().value);
        while (moveAhead().type == Token.Type.TK_NAMESPACE_DELIMITER) {
            name.append("\\").append(moveAhead().value);
        }

        return name.toString();
    }

    public int getTokPrecedence() {
        if (!binOperationPrecedence.containsKey(lexer.currentToken().value)) {
            //lexer.input = new StringBuilder(lexer.currentToken().value+" "+lexer.input);
            return -1;
        }

        int tokPrec = binOperationPrecedence.get(lexer.currentToken().value);
        if (tokPrec < 0)
            return -1;

        return tokPrec;
    }

    public Type parseType(boolean isFunc) throws KayJamLexerException, KayJamParserException {
        Type type = Type.getType(parseName(), isFunc);
        if (currentTokenType() == Token.Type.TK_NULLABLE) {
            if (type.equals(Type.VOID))
                throw new KayJamParserException(lexer, "Void cannot be nullable");

            type.nullable = true;
            moveAhead();
        }

        return type;
    }

    public List<Argument> parseArguments() throws KayJamLexerException, KayJamParserException {
        List<Argument> arguments = new ArrayList<>();
        while (true) {
            moveAhead();
            if (lexer.currentToken().type == Token.Type.TK_CLOSE)
                break;

            String name = lexer.currentToken().value;
            Type argType = parseType(false);

            Token t = lexer.currentToken();
            if (t.type == Token.Type.IDENTIFIER) {
                arguments.add(new Argument(argType, t.value));
                t = moveAhead();
            } else if (t.type == Token.Type.TK_COMMA || t.type == Token.Type.TK_CLOSE)
                arguments.add(new Argument(Type.ANY, name));

            if (t.type == Token.Type.TK_CLOSE)
                break;
            else if (t.type != Token.Type.TK_COMMA)
                throw new KayJamParserException(lexer, "expected comma \",\"");
        }

        return arguments;
    }

    public Expression parseBinOpRHS(int exprPrec, Expression lhs) throws KayJamLexerException,
            KayJamParserException {
        while (true) {
            int tokPrec = getTokPrecedence();

            if (tokPrec < exprPrec) {
                lexer.input = new StringBuilder(lexer.currentToken().value + " " + lexer.input);
                return lhs;
            }

            Token binOp = lexer.currentToken();

            KayJamParserKeywords kayJamIdentifier = KayJamParserKeywords.find(binOp.value);
            int line = lexer.getLine();

            if (kayJamIdentifier == KayJamParserKeywords.CAST) {
                moveAhead();
                lhs = new CastExpression(lhs, parseType(false), line);
            } else if (kayJamIdentifier == KayJamParserKeywords.IS) {
                moveAhead();
                lhs = new IsExpression(lhs, parseType(false), line);
            } else {
                moveAhead();
                Expression rhs = readPrimary();

                moveAhead();

                if (binOp.type == Token.Type.TK_ACCESS) {
                    lhs = new AccessExpression(lhs, rhs, line);
                } else if (binOp.type == Token.Type.TK_RANGE) {
                    lhs = new OperationExpression(lhs, rhs, Operation.RANGE, line);
                } else {
                    int nextPrec = getTokPrecedence();
                    if (tokPrec < nextPrec)
                        rhs = parseBinOpRHS(nextPrec, rhs);
                    lhs = new OperationExpression(lhs, rhs, Operation.get(binOp.type), line);
                }
            }
        }
    }

    public KayJamFile fillFile() throws KayJamParserException, KayJamLexerException {
        if (KayJamParserKeywords.find(lexer.currentToken().value)
                == KayJamParserKeywords.NAMESPACE) {
            moveAhead();

            file.namespace = parseName();
            moveAhead();
        }

        while (KayJamParserKeywords.find(lexer.currentToken().value) == KayJamParserKeywords.USE) {
            int line = lexer.getLine();
            moveAhead();

            Type usedImport = parseType(false);
            file.usages.add(new KayJamFile.Usage(
                    usedImport, null, line
            ));

            requireToken(Token.Type.TK_SEMI, false);
            moveAhead();
        }

        while (!lexer.isFinished()) {
            while (lexer.currentToken().type == Token.Type.TK_SEMI) {
                moveAhead();
            }

            if(lexer.isFinished()) {
               break;
            }

            List<Annotation> statementAnnotations = readAnnotations();
            AccessType statementAccessType = readAccessType();

            switch (KayJamParserKeywords.find(lexer.currentToken().value)) {
                case CONSTANT ->
                        file.constants.add(ConstantParser.parse(statementAnnotations, statementAccessType, this));
                case FUNCTION ->
                        file.functions.add(FunctionStatementParser.parse(statementAnnotations, statementAccessType, this));
                case CLASS ->
                        file.classes.add(ClassStatementParser.parse(statementAnnotations, statementAccessType, this));
                case OBJECT ->
                        file.classes.add(ObjectStatementParser.parse(statementAnnotations, statementAccessType, this));
                case TYPEALIAS ->
                        file.typeAliases.add(TypeAliasStatementParser.parse(statementAnnotations, statementAccessType, this));
                case null, default -> {
                    if (!statementAnnotations.isEmpty()) {
                        throw new KayJamParserException(
                                statementAnnotations.getFirst().line,
                                "Current statement not support annotations"
                        );
                    }

                    if (statementAccessType != AccessType.NONE) {
                        throw new KayJamParserException(
                                lexer,
                                "Current statement not support access types"
                        );
                    }

                    file.children.add(readStatement());
                }
            }

            moveAhead();
            while (lexer.currentToken().type == Token.Type.TK_SEMI) {
                moveAhead();
            }
        }

        return file;
    }

    public List<Statement> parseStatements() throws KayJamParserException, KayJamLexerException {
        return parseStatements(true);
    }

    public List<Statement> parseStatements(boolean requireOpenBracket) throws KayJamParserException, KayJamLexerException {
        if (requireOpenBracket && lexer.currentToken().type != Token.Type.OPEN_BRACKET)
            throw new KayJamParserException(lexer, "Expected open bracket");

        if (requireOpenBracket) {
            if (lexer.currentToken().type != Token.Type.OPEN_BRACKET)
                throw new KayJamParserException(lexer, "Expected open bracket");

            moveAhead();
        }

        List<Statement> statements = new ArrayList<>();
        while (lexer.currentToken().type != Token.Type.CLOSE_BRACKET) {
            while (lexer.currentToken().type == Token.Type.TK_SEMI) {
                moveAhead();
            }

            if (lexer.currentToken().type == Token.Type.CLOSE_BRACKET) {
                break;
            }

            statements.add(readStatement());
            moveAhead();
            if (lexer.currentToken().type == Token.Type.TK_SEMI) {
                moveAhead();
            }
        }

        return statements;
    }

    void throwSemicolon() throws KayJamParserException {
        throw new KayJamParserException(lexer, "A semicolon was expected, but it wasn't there. Please put it on!");
    }
}
