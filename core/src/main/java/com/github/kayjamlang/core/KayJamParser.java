package com.github.kayjamlang.core;

import com.github.kayjamlang.core.containers.*;
import com.github.kayjamlang.core.exceptions.KayJamParserException;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.expressions.data.Annotation;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.expressions.loops.ForExpression;
import com.github.kayjamlang.core.expressions.data.Operation;
import com.github.kayjamlang.core.expressions.loops.WhileExpression;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return readExpression(AccessType.NONE, new ArrayList<>());
    }

    public Expression readTopExpression() throws KayJamLexerException, KayJamParserException {
        return readTopExpression(AccessType.NONE, new ArrayList<>());
    }

    public Expression readTopExpression(AccessType identifier, List<Annotation> annotations) throws KayJamLexerException, KayJamParserException {
        Expression expression = readPrimary(identifier, annotations);
        if(currentTokenType() == Token.Type.CLOSE_BRACKET)
            return expression;


        moveAhead();
        expression = readEndExpression(expression);
        expression = parseBinOpRHS(identifier, annotations, 0, expression);

        moveAhead();
        while(currentTokenType() == Token.Type.TK_OPEN_SQUARE_BRACKET){
            int line = lexer.getLine();

            moveAhead();
            expression = new GetExpression(expression, readExpression(), line);
            requireToken(Token.Type.TK_CLOSE_SQUARE_BRACKET);

            moveAhead();
            expression = readEndExpression(expression);
        }

        expression = parseBinOpRHS(identifier, annotations, 0, expression);
        return expression;
    }

    public Expression readExpression(AccessType identifier, List<Annotation> annotations) throws KayJamLexerException, KayJamParserException {
        Expression expression = readTopExpression(identifier, annotations);
        if(expression instanceof ClassContainer)
            throw new KayJamParserException(lexer, "This expression is not allowed to be used in this place.");

        return expression;
    }

    public Expression readEndExpression(Expression root) throws KayJamLexerException {
        switch (currentTokenType()) {
            case TK_NOT:
                moveAhead();
                return new AssertNullExpression(root, lexer.getLine());
            case TK_PLUS_ONE:
                moveAhead();
                return new OperationExpression(root,
                        new ValueExpression(1), Operation.PLUS, lexer.getLine());
            case TK_MINUS_ONE:
                moveAhead();
                return new OperationExpression(root,
                        new ValueExpression(1), Operation.MINUS, lexer.getLine());
            default: return root;
        }
    }

    public Token.Type currentTokenType(){
        return lexer.currentToken().type;
    }

    public Token moveAhead() throws KayJamLexerException {
        lexer.moveAhead();
        if(!lexer.isSuccessful())
            throw new KayJamLexerException(lexer, lexer.errorMessage());

        return lexer.currentToken();
    }

    public Token requireToken(Token.Type type) throws KayJamLexerException, KayJamParserException {
        Token token = moveAhead();
        if(token.type!=type)
            throw new KayJamParserException(lexer, "expected "+type.name().toLowerCase()+", not "+token.value);

        return lexer.currentToken();
    }

    public void requireIdentifier(KayJamParserKeywords identifier) throws KayJamLexerException, KayJamParserException {
        Token token = requireToken(Token.Type.IDENTIFIER);
        if(KayJamParserKeywords.find(token.value)!=identifier)
            throw new KayJamParserException(lexer, "expected "+identifier.name());

    }

    public Expression readPrimary(AccessType identifier, List<Annotation> annotations) throws KayJamLexerException,
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

                            return new VariableExpression(name, expression, identifier, line);
                        }
                        case FUNCTION: {
                            String name = requireToken(Token.Type.IDENTIFIER).value;

                            requireToken(Token.Type.TK_OPEN);

                            List<Argument> arguments = parseArguments();

                            Type returnType = Type.VOID;
                            if (moveAhead().type == Token.Type.TK_COLON) {
                                requireToken(Token.Type.IDENTIFIER);

                                returnType = parseType(true);
                            }

                            List<Expression> body = parseExpressions();

                            return new FunctionContainer(name, body, identifier, arguments, returnType, annotations, line);
                        }
                        case NAMED: {
                            requireIdentifier(KayJamParserKeywords.FUNCTION);
                            String name = requireToken(Token.Type.IDENTIFIER).value;

                            moveAhead();
                            return new NamedExpressionFunctionContainer(name, parseExpressions(), identifier, line);
                        }
                        case PRIVATE:
                            moveAhead();
                            return readExpression(AccessType.PRIVATE, annotations);
                        case PUBLIC:
                            moveAhead();
                            return readExpression(AccessType.PUBLIC, annotations);
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
                            if(moveAhead().value.equals("void"))
                                return new ReturnExpression(null, line);
                            return new ReturnExpression(readExpression(), line);

                        case CONSTRUCTOR: {
                            requireToken(Token.Type.TK_OPEN);
                            List<Argument> arguments = parseArguments();

                            moveAhead();
                            return new ConstructorContainer(arguments, parseExpressions(), identifier, line);
                        }
                        case COMPANION:
                            moveAhead();
                            return readExpression(AccessType.COMPANION, annotations);
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
                            throw new KayJamParserException(lexer, "\""+lexer.currentToken().value+"\" is in the wrong place");
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
                            return new NamedExpression(name, readExpression(), line);
                        default:
                            lexer.input = new StringBuilder(lexer.currentToken().value + lexer.input);
                            return new VariableLinkExpression(name, line);
                    }
                }
            }
            case TK_ANNOTATION: {
                String name = requireToken(Token.Type.IDENTIFIER).value;

                moveAhead();
                if (currentTokenType() == Token.Type.TK_OPEN) {

                    moveAhead();
                    Expression value = readExpression();
                    if (!(value instanceof ValueExpression))
                        throw new KayJamParserException(lexer, "Invalid value of annotation");

                    annotations.add(new Annotation(name, (ValueExpression) value));
                    requireToken(Token.Type.TK_CLOSE);

                    moveAhead();
                } else annotations.add(new Annotation(name));

                return readExpression(identifier, annotations);
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
                return new NegationExpression(readExpression(identifier, annotations), line);
            case TK_BITWISE_COMPLEMENT:
                moveAhead();
                return new BitwiseComplementExpression(readExpression(identifier, annotations), line);
            case OPEN_BRACKET:
                return new Container(parseExpressions(), AccessType.PUBLIC, line);
            case TK_OPEN_SQUARE_BRACKET: {
                List<Expression> values = new ArrayList<>();
                while (moveAhead().type != Token.Type.TK_CLOSE_SQUARE_BRACKET) {
                    values.add(readExpression(identifier, annotations));

                    Token token = moveAhead();
                    if (token.type == Token.Type.TK_CLOSE_SQUARE_BRACKET)
                        break;
                    else if (token.type != Token.Type.TK_COMMA)
                        throw new KayJamParserException(line, "expected comma");
                }

                return new ArrayExpression(values, line);
            }

            case TK_SEMI:
                moveAhead();
                System.out.println(lexer.getLine());
                return readPrimary(identifier, annotations);

            default:
                Expression expression = parseValue();
                if(expression!=null) return expression;
                throw new KayJamParserException(lexer, "\""+lexer.currentToken().value+"\" is in the wrong place");
        }
    }

    public Expression parseValue() throws KayJamLexerException {
        if(currentTokenType()==Token.Type.TK_MINUS){
            moveAhead();

            return new OperationExpression(new ValueExpression(-1), parseValue(),
                    Operation.MULTIPLY, lexer.getLine());
        }

        switch (currentTokenType()) {
            case STRING:
                return new ValueExpression(lexer.currentToken().value.substring(1, lexer.currentToken().value.length() - 1));
            case NULL:
                return new ValueExpression(null);
            case LONG:
                return new ValueExpression(Long.parseLong(lexer.currentToken()
                        .value.substring(0, lexer.currentToken().value.length() - 1)));
            case INTEGER:
                return new ValueExpression(Integer.parseInt(lexer.currentToken().value));
            case REAL:
                return new ValueExpression(Double.parseDouble(lexer.currentToken().value));
            case BOOL:
                return new ValueExpression(lexer.currentToken().value.charAt(0)=='t');
        }

        return null;
    }

    public List<String> parseRequiredUsages(String root) throws KayJamLexerException, KayJamParserException {
        List<String> usages = new ArrayList<>();
        if(currentTokenType()==Token.Type.OPEN_BRACKET){
            while (moveAhead().type!=Token.Type.CLOSE_BRACKET){
                usages.addAll(parseRequiredUsages(root));

                if(currentTokenType()==Token.Type.CLOSE_BRACKET)
                    break;
                else if(currentTokenType()!=Token.Type.TK_COMMA)
                    throw new KayJamParserException(lexer, "excepted comma");
            }

            moveAhead();
        }else{
            String name = root+parseName();
            if(currentTokenType()==Token.Type.OPEN_BRACKET){
                usages.addAll(parseRequiredUsages(name));
            }else usages.add(name);
        }

        return usages;
    }

    public String parseName() throws KayJamLexerException, KayJamParserException {
        if(currentTokenType()==Token.Type.TK_NAMESPACE_DELIMITER)
            moveAhead();

        if(currentTokenType()!=Token.Type.IDENTIFIER)
            throw new KayJamParserException(lexer, "excepted type");

        StringBuilder name = new StringBuilder("\\"+lexer.currentToken().value);
        while (moveAhead().type==Token.Type.TK_NAMESPACE_DELIMITER){
            name.append("\\").append(moveAhead().value);
        }

        return name.toString();
    }

    public int getTokPrecedence(){
        if(!binOperationPrecedence.containsKey(lexer.currentToken().value)) {
            //lexer.input = new StringBuilder(lexer.currentToken().value+" "+lexer.input);
            return -1;
        }

        int tokPrec = binOperationPrecedence.get(lexer.currentToken().value);
        if(tokPrec<0)
            return -1;

        return tokPrec;
    }

    public Type parseType(boolean isFunc) throws KayJamLexerException, KayJamParserException {
        Type type = Type.getType(parseName(), isFunc);
        if(currentTokenType()==Token.Type.TK_NULLABLE){
            if(type.equals(Type.VOID))
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
            if(t.type == Token.Type.IDENTIFIER) {
                arguments.add(new Argument(argType, t.value));
                t = moveAhead();
            }else if(t.type == Token.Type.TK_COMMA||t.type == Token.Type.TK_CLOSE)
                arguments.add(new Argument(Type.ANY, name));

            if (t.type == Token.Type.TK_CLOSE)
                break;
            else if (t.type != Token.Type.TK_COMMA)
                throw new KayJamParserException(lexer, "expected comma \",\"");
        }

        return arguments;
    }

    public Expression parseBinOpRHS(AccessType identifier, List<Annotation> annotations,
                                    int exprPrec, Expression lhs) throws KayJamLexerException,
            KayJamParserException {
        while (true) {
            int tokPrec = getTokPrecedence();

            if(tokPrec<exprPrec) {
                lexer.input = new StringBuilder(lexer.currentToken().value+" "+lexer.input);
                return lhs;
            }

            Token binOp = lexer.currentToken();

            KayJamParserKeywords kayJamIdentifier = KayJamParserKeywords.find(binOp.value);
            int line = lexer.getLine();

            if(kayJamIdentifier== KayJamParserKeywords.CAST) {
                moveAhead();
                lhs = new CastExpression(lhs, parseType(false), line);
            }else if(kayJamIdentifier== KayJamParserKeywords.IS) {
                moveAhead();
                lhs = new IsExpression(lhs, parseType(false), line);
            }else{
                moveAhead();
                Expression rhs = readPrimary(identifier, annotations);

                moveAhead();

                if (binOp.type == Token.Type.TK_ACCESS) {
                    lhs = new AccessExpression(lhs, rhs, line);
                }else if (binOp.type == Token.Type.TK_RANGE) {
                    lhs = new OperationExpression(lhs, rhs, Operation.RANGE, line);
                }else{
                    int nextPrec = getTokPrecedence();
                    if (tokPrec < nextPrec)
                        rhs = parseBinOpRHS(identifier, annotations, nextPrec, rhs);
                    lhs = new OperationExpression(lhs, rhs, Operation.get(binOp.type), line);
                }
            }
        }
    }

    public KayJamFile fillFile() throws KayJamParserException, KayJamLexerException {
        if (KayJamParserKeywords.find(lexer.currentToken().value)
                ==KayJamParserKeywords.NAMESPACE) {
            moveAhead();

            file.namespace = parseName();
            moveAhead();
        }

        while(KayJamParserKeywords.find(lexer.currentToken().value) ==KayJamParserKeywords.USE){
            int line = lexer.getLine();
            moveAhead();
            List<String> needed = parseRequiredUsages("");
            if (!lexer.currentToken().value.equals("from"))
                throw new KayJamParserException(lexer, "excepted keyword 'from'");

            String from = requireToken(Token.Type.STRING).value;
            file.usages.add(new KayJamFile.Usage(needed,
                    from.substring(1, from.length() - 1), line));

            requireToken(Token.Type.TK_SEMI);
            moveAhead();
        }

        file.constants = parseConstants();

        AccessType type = AccessType.PUBLIC;
        while (!lexer.isFinished()) {
            int line = lexer.getLine();
            KayJamParserKeywords keyword = KayJamParserKeywords.find(lexer.currentToken().value);
            if(keyword == null){
                file.children.add(readTopExpression());

                boolean closeBracket = lexer.currentToken().type == Token.Type.CLOSE_BRACKET;
                if (!closeBracket&&!lexer.isFinished()&&moveAhead().type!=Token.Type.TK_SEMI)
                    throwSemicolon();

                moveAhead();
            }else{
                switch (keyword){
                    case PUBLIC:
                        moveAhead();
                        type = AccessType.PUBLIC;
                    break;

                    case PRIVATE:
                        moveAhead();
                        type = AccessType.PRIVATE;
                    break;

                    case CLASS:
                        String name = requireToken(Token.Type.IDENTIFIER).value;
                        String extendsClass = null;
                        List<String> implementsClass = new ArrayList<>();

                        while (moveAhead().type != Token.Type.OPEN_BRACKET) {
                            if (currentTokenType() == Token.Type.TK_COMPANION_ACCESS)
                                implementsClass.add(requireToken(Token.Type.IDENTIFIER).value);
                            else if (currentTokenType() == Token.Type.TK_COLON && extendsClass == null)
                                extendsClass = requireToken(Token.Type.IDENTIFIER).value;
                            else
                                throw new KayJamParserException(lexer, "expected open bracket or extends/implements token");
                        }

                        file.classes.put(name, new ClassContainer(name, extendsClass, implementsClass,
                                parseExpressions(), type, line));

                        moveAhead();
                        break;

                    case OBJECT:
                        name = requireToken(Token.Type.IDENTIFIER).value;
                        moveAhead();

                        moveAhead();
                        Map<String, Expression> constants = parseConstants();

                        file.classes.put(name, new ObjectContainer(name, constants,
                                parseExpressions(false), type, line));

                        moveAhead();
                        break;
                }
            }
        }

        return file;
    }

    public Map<String, Expression> parseConstants() throws KayJamParserException, KayJamLexerException {
        HashMap<String, Expression> constants = new HashMap<>();
        while(KayJamParserKeywords.find(lexer.currentToken().value)==KayJamParserKeywords.CONSTANT){
            String name = requireToken(Token.Type.IDENTIFIER).value;
            requireToken(Token.Type.TK_ASSIGN);

            moveAhead();
            Expression expression = readExpression();
            if (expression!=null && expression.isConstant())
                constants.put(name, expression);
            else throw new KayJamParserException(lexer.getLine(),
                    "Expression cannot be constant");

            requireToken(Token.Type.TK_SEMI);
            moveAhead();
        }

        return constants;
    }

    public List<Expression> parseExpressions() throws KayJamParserException, KayJamLexerException {
        return parseExpressions(true);
    }

    public List<Expression> parseExpressions(boolean requireOpenBracket) throws KayJamParserException, KayJamLexerException {
        if(requireOpenBracket && lexer.currentToken().type!=Token.Type.OPEN_BRACKET)
            throw new KayJamParserException(lexer, "Expected open bracket");

        if(requireOpenBracket) {
            if(lexer.currentToken().type!=Token.Type.OPEN_BRACKET)
                throw new KayJamParserException(lexer, "Expected open bracket");

            moveAhead();
        }

        List<Expression> expressions = new ArrayList<>();
        while (lexer.currentToken().type != Token.Type.CLOSE_BRACKET) {
            expressions.add(readExpression());

            if(moveAhead().type == Token.Type.TK_SEMI) {
                moveAhead();
            }
        }

        return expressions;
    }

    void throwSemicolon() throws KayJamParserException {
        throw new KayJamParserException(lexer, "A semicolon was expected, but it wasn't there. Please put it on!");
    }
}
