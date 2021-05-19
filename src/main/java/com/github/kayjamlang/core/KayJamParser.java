package com.github.kayjamlang.core;

import com.github.kayjamlang.core.containers.*;
import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.exceptions.LexerException;
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

    private final KayJamLexer lexer;
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
        binOperationPrecedence.put("&&", 70);
        binOperationPrecedence.put("||", 70);
    }

    public KayJamParser(KayJamLexer lexer) {
        this.lexer = lexer;
    }

    public Expression readExpression() throws LexerException, ParserException {
        return readExpression(AccessType.NONE, new ArrayList<>());
    }

    public Expression readTopExpression() throws LexerException, ParserException {
        return readTopExpression(AccessType.NONE, new ArrayList<>());
    }

    public Expression readTopExpression(AccessType identifier, List<Annotation> annotations) throws LexerException, ParserException {
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

    public Expression readExpression(AccessType identifier, List<Annotation> annotations) throws LexerException, ParserException {
        Expression expression = readTopExpression(identifier, annotations);
        if(expression instanceof ClassContainer||
                expression instanceof UseExpression)
            throw new ParserException(lexer, "This expression is not allowed to be used in this place.");

        return expression;
    }

    public Expression readEndExpression(Expression root) throws LexerException {
        if(currentTokenType()==Token.Type.TK_NOT) {
            moveAhead();
            return new AssertNullExpression(root, lexer.getLine());
        }else if(currentTokenType()==Token.Type.TK_PLUS_ONE){
            moveAhead();
            return new OperationExpression(root,
                    new ValueExpression(1), Operation.PLUS, lexer.getLine());
        }else if(currentTokenType()==Token.Type.TK_MINUS_ONE){
            moveAhead();
            return new OperationExpression(root,
                    new ValueExpression(1), Operation.MINUS, lexer.getLine());
        }

        return root;
    }

    public Token.Type currentTokenType(){
        return lexer.currentToken().type;
    }

    public Token moveAhead() throws LexerException {
        lexer.moveAhead();
        if(!lexer.isSuccessful())
            throw new LexerException(lexer.errorMessage());

        return lexer.currentToken();
    }

    public Token requireToken(Token.Type type) throws LexerException, ParserException {
        Token token = moveAhead();
        if(token.type!=type)
            throw new ParserException(lexer, "expected "+type.name().toLowerCase());

        return lexer.currentToken();
    }

    public void requireIdentifier(KayJamIdentifier identifier) throws LexerException, ParserException {
        Token token = requireToken(Token.Type.IDENTIFIER);
        if(KayJamIdentifier.find(token.value)!=identifier)
            throw new ParserException(lexer, "expected "+identifier.name());

    }

    public Expression readPrimary(AccessType identifier, List<Annotation> annotations) throws LexerException,
            ParserException {
        Token.Type type = currentTokenType();
        int line = lexer.getLine();

        if(type == Token.Type.IDENTIFIER){
            KayJamIdentifier keyword = KayJamIdentifier.find(lexer.currentToken().value);
            if(keyword!=null){
                if(keyword==KayJamIdentifier.VAR) {
                    String name = requireToken(Token.Type.IDENTIFIER).value;
                    requireToken(Token.Type.TK_ASSIGN);

                    moveAhead();
                    Expression expression = readExpression();

                    return new VariableExpression(name, expression, identifier, line);
                }else if(keyword==KayJamIdentifier.FUNCTION){
                    String name = requireToken(Token.Type.IDENTIFIER).value;

                    requireToken(Token.Type.TK_OPEN);

                    List<Argument> arguments = parseArguments();

                    Type returnType = Type.VOID;
                    if(moveAhead().type==Token.Type.TK_COLON){
                        requireToken(Token.Type.IDENTIFIER);

                        returnType = parseType(true);
                    }

                    List<Expression> body = parseExpressions();

                    return new FunctionContainer(name, body, identifier, arguments, returnType, annotations, line);
                }else if(keyword==KayJamIdentifier.NAMED){
                    requireIdentifier(KayJamIdentifier.FUNCTION);
                    String name = requireToken(Token.Type.IDENTIFIER).value;

                    moveAhead();
                    return new NamedExpressionFunctionContainer(name, parseExpressions(), identifier, line);
                }else if(keyword==KayJamIdentifier.PRIVATE) {
                    moveAhead();
                    return readExpression(AccessType.PRIVATE, annotations);
                }else if(keyword==KayJamIdentifier.PUBLIC) {
                    moveAhead();
                    return readExpression(AccessType.PUBLIC, annotations);
                }else if(keyword==KayJamIdentifier.WHILE){
                    requireToken(Token.Type.TK_OPEN);

                    moveAhead();
                    Expression condition = readExpression();

                    if(currentTokenType()!= Token.Type.TK_CLOSE)
                        throw new ParserException(lexer, "expected close \")\"");
                    else moveAhead();

                    moveAhead();
                    return new WhileExpression(condition, readExpression(), line);
                }else if(keyword==KayJamIdentifier.FOR){
                    requireToken(Token.Type.TK_OPEN);

                    String name = requireToken(Token.Type.IDENTIFIER).value;
                    requireIdentifier(KayJamIdentifier.IN);

                    moveAhead();
                    Expression range = readExpression();

                    if(currentTokenType()!= Token.Type.TK_CLOSE)
                        throw new ParserException(lexer, "expected close \")\"");
                    else moveAhead();

                    moveAhead();
                    return new ForExpression(name, range, readExpression(), line);
                }else if(keyword==KayJamIdentifier.OBJECT){
                    Token t = moveAhead();
                    if(t.type==Token.Type.OPEN_BRACKET)
                        return new ObjectContainer(parseExpressions(), identifier, line);
                    else if(t.type==Token.Type.IDENTIFIER){
                        moveAhead();
                        return new ObjectContainer(t.value,
                                parseExpressions(), identifier, line);
                    }else throw new ParserException(lexer, "expected name of object or open bracket");
                }else if(keyword==KayJamIdentifier.CLASS){
                    moveAhead();
                    type = lexer.currentToken().type;
                    if(type == Token.Type.IDENTIFIER){
                        String name = lexer.currentToken().value;
                        String extendsClass = null;
                        List<String> implementsClass = new ArrayList<>();

                        while (moveAhead().type!=Token.Type.OPEN_BRACKET){
                            if(currentTokenType()==Token.Type.TK_COMPANION_ACCESS)
                                implementsClass.add(requireToken(Token.Type.IDENTIFIER).value);
                            else if(currentTokenType()==Token.Type.TK_COLON&&extendsClass==null)
                                extendsClass = requireToken(Token.Type.IDENTIFIER).value;
                            else throw new ParserException(lexer, "expected open bracket or extends/implements token");
                        }

                        return new ClassContainer(name, extendsClass, implementsClass,
                                parseExpressions(), identifier, line);
                    }else throw new ParserException(lexer, "expected identifier of class");
                }else if(keyword==KayJamIdentifier.RETURN){
                    moveAhead();
                    return new ReturnExpression(readExpression(), line);
                }else if(keyword==KayJamIdentifier.CONSTRUCTOR){
                    requireToken(Token.Type.TK_OPEN);
                    List<Argument> arguments = parseArguments();

                    moveAhead();
                    return new ConstructorContainer(arguments, parseExpressions(), identifier, line);
                }else if(keyword==KayJamIdentifier.USE){
                    moveAhead();
                    List<String> needed = parseRequiredUsages("");
                    if(!lexer.currentToken().value.equals("from"))
                        throw new ParserException(lexer, "excepted keyword 'from'");

                    String from = requireToken(Token.Type.STRING).value;
                    return new UseExpression(needed,
                            from.substring(1, from.length()-1), line);
                }else if(keyword==KayJamIdentifier.COMPANION) {
                    moveAhead();
                    return readExpression(AccessType.COMPANION, annotations);
                }else if(keyword==KayJamIdentifier.IF){
                    requireToken(Token.Type.TK_OPEN);

                    moveAhead();
                    Expression condition = readExpression();

                    requireToken(Token.Type.TK_CLOSE);

                    moveAhead();
                    Expression ifTrue = readExpression();
                    Expression ifFalse = null;

                    if(KayJamIdentifier.find(moveAhead().value) == KayJamIdentifier.ELSE){
                        moveAhead();
                        ifFalse = readExpression();
                    }else {
                        lexer.input = new StringBuilder("}"+lexer.currentToken().value+lexer.input);
                        moveAhead();
                    }

                    return new IfExpression(condition, ifTrue, ifFalse, line);
                }else if(keyword==KayJamIdentifier.PACK){
                    moveAhead();
                    String name = parseName();
                    if(lexer.currentToken().type!=Token.Type.OPEN_BRACKET)
                        throw new ParserException(lexer, "Expected open bracket");

                    List<Expression> expressions = new ArrayList<>();

                    while (moveAhead().type != Token.Type.CLOSE_BRACKET) {
                        expressions.add(readExpression());

                        boolean closeBracket = lexer.currentToken().type == Token.Type.CLOSE_BRACKET;

                        if (!closeBracket&&moveAhead().type!=Token.Type.TK_SEMI)
                            throw new ParserException(lexer,
                                    "A semicolon was expected, but it wasn't there. Please put it on!");
                    }

                    return new PackContainer(name, new Container(expressions, line),
                            false);
                }else if(keyword==KayJamIdentifier.CONSTANT){
                    String name = requireToken(Token.Type.IDENTIFIER).value;
                    requireToken(Token.Type.TK_ASSIGN);

                    moveAhead();
                    Expression expression = readExpression();
                    if(expression instanceof ValueExpression)
                        return new ConstantValueExpression(name, (ValueExpression) expression, line);
                    else throw new ParserException(expression.line,
                            "Expression cannot be constant");
                }
            }else{
                String name = lexer.currentToken().value;

                type = moveAhead().type;
                if (type == Token.Type.TK_ASSIGN) {
                    moveAhead();
                    Expression expression = readExpression();
                    return new VariableSetExpression(name, expression, line);
                } else if (type == Token.Type.TK_OPEN) {
                    List<Expression> arguments = new ArrayList<>();
                    while (moveAhead().type != Token.Type.TK_CLOSE) {
                        arguments.add(readExpression());

                        Token token = moveAhead();
                        if (token.type == Token.Type.TK_CLOSE)
                            break;
                        else if (token.type != Token.Type.TK_COMMA)
                            throw new ParserException(lexer, "expected comma \",\"");
                    }

                    return new CallOrCreateExpression(name, arguments, line);
                } else if (type == Token.Type.TK_ACCESS) {
                    moveAhead();
                    return new AccessExpression(new VariableLinkExpression(name, line), readExpression(), line);
                } else if (type == Token.Type.TK_COMPANION_ACCESS) {
                    moveAhead();
                    return new CompanionAccessExpression(name, readExpression(), line);
                } else if (type == Token.Type.OPEN_BRACKET || type == Token.Type.TK_REF) {
                    if (type == Token.Type.TK_REF)
                        moveAhead();

                    return new NamedExpression(name, readExpression(), line);
                } else {
                    lexer.input = new StringBuilder(lexer.currentToken().value + lexer.input);
                    return new VariableLinkExpression(name, line);
                }
            }
        }else if(type == Token.Type.TK_ANNOTATION){
            String name = requireToken(Token.Type.IDENTIFIER).value;

            moveAhead();
            if(currentTokenType()==Token.Type.TK_OPEN){

                moveAhead();
                Expression value = readExpression();
                if(!(value instanceof ValueExpression))
                    throw new ParserException(lexer, "Invalid value of annotation");

                annotations.add(new Annotation(name, (ValueExpression) value));
                requireToken(Token.Type.TK_CLOSE);

                moveAhead();
            }else annotations.add(new Annotation(name));

            return readExpression(identifier, annotations);
        }else if(type == Token.Type.TK_OPEN){
            moveAhead();

            Expression expression = readExpression();
            requireToken(Token.Type.TK_CLOSE);

            return expression;
        }else if(type == Token.Type.TK_REF){
            List<Argument> arguments = new ArrayList<>();
            if(moveAhead().type==Token.Type.TK_OPEN) {
                arguments = parseArguments();
                moveAhead();
            }

            Type returnType = Type.VOID;
            if(moveAhead().type==Token.Type.TK_COLON){
                requireToken(Token.Type.IDENTIFIER);

                returnType = parseType(true);
            }

            return new FunctionRefExpression(arguments, readExpression(), returnType, line);
        }else if(type == Token.Type.TK_NOT){
            moveAhead();
            return new NegationExpression(readExpression(identifier, annotations), line);
        }else if(type == Token.Type.OPEN_BRACKET){
            return new Container(parseExpressions(), AccessType.PUBLIC, line);
        }else if(type == Token.Type.TK_OPEN_SQUARE_BRACKET){
            List<Expression> values = new ArrayList<>();
            while (moveAhead().type!=Token.Type.TK_CLOSE_SQUARE_BRACKET){
                values.add(readExpression(identifier, annotations));

                Token token = moveAhead();
                if(token.type==Token.Type.TK_CLOSE_SQUARE_BRACKET)
                    break;
                else if(token.type!=Token.Type.TK_COMMA)
                    throw new ParserException(line, "expected comma");
            }

            return new ArrayExpression(values, line);
        }else if(type == Token.Type.STRING){
            return new ValueExpression(lexer.currentToken().value.substring(1, lexer.currentToken().value.length()-1));
        }else if(type == Token.Type.NULL){
            return new ValueExpression(null);
        }else if(type == Token.Type.LONG){
            return new ValueExpression(Long.parseLong(lexer.currentToken()
                    .value.substring(0, lexer.currentToken().value.length()-1)));
        }else if(type == Token.Type.INTEGER){
            return new ValueExpression(Integer.parseInt(lexer.currentToken().value));
        }else if(type == Token.Type.REAL){
            return new ValueExpression(Double.parseDouble(lexer.currentToken().value));
        }else if(type == Token.Type.BOOL){
            return new ValueExpression(lexer.currentToken().value.equals("true"));
        }else if(type == Token.Type.TK_SEMI){
            moveAhead();
            return readPrimary(identifier, annotations);
        }else if(type == Token.Type.TK_MINUS){
            moveAhead();
            return new OperationExpression(new ValueExpression(-1), readExpression(identifier, annotations),
                    Operation.MULTIPLY, line);
        }

        throw new ParserException(lexer, "\""+lexer.currentToken().value+"\" is in the wrong place");
    }

    public List<String> parseRequiredUsages(String root) throws LexerException, ParserException {
        List<String> usages = new ArrayList<>();
        if(currentTokenType()==Token.Type.OPEN_BRACKET){
            while (moveAhead().type!=Token.Type.CLOSE_BRACKET){
                usages.addAll(parseRequiredUsages(root));

                if(currentTokenType()==Token.Type.CLOSE_BRACKET)
                    break;
                else if(currentTokenType()!=Token.Type.TK_COMMA)
                    throw new ParserException(lexer, "excepted comma");
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

    public String parseName() throws LexerException, ParserException {
        if(currentTokenType()==Token.Type.TK_NAMESPACE_DELIMITER)
            moveAhead();

        if(currentTokenType()!=Token.Type.IDENTIFIER)
            throw new ParserException(lexer, "excepted type");

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

    public Type parseType(boolean isFunc) throws LexerException, ParserException {
        Type type = Type.getType(parseName(), isFunc);
        if(currentTokenType()==Token.Type.TK_NULLABLE){
            if(type.equals(Type.VOID))
                throw new ParserException(lexer, "Void cannot be nullable");

            type.nullable = true;
            moveAhead();
        }

        return type;
    }

    public List<Argument> parseArguments() throws LexerException, ParserException {
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
                throw new ParserException(lexer, "expected comma \",\"");
        }

        return arguments;
    }

    public Expression parseBinOpRHS(AccessType identifier, List<Annotation> annotations,
                                    int exprPrec, Expression lhs) throws LexerException,
                                        ParserException {
        while (true) {
            int tokPrec = getTokPrecedence();

            if(tokPrec<exprPrec) {
                lexer.input = new StringBuilder(lexer.currentToken().value+" "+lexer.input);
                return lhs;
            }

            Token binOp = lexer.currentToken();

            KayJamIdentifier kayJamIdentifier = KayJamIdentifier.find(binOp.value);
            int line = lexer.getLine();

            if(kayJamIdentifier==KayJamIdentifier.CAST) {
                moveAhead();
                lhs = new CastExpression(lhs, parseType(false), line);
            }else if(kayJamIdentifier==KayJamIdentifier.IS) {
                moveAhead();
                lhs = new IsExpression(lhs, parseType(false), line);
            }else{
                moveAhead();
                Expression rhs = readPrimary(identifier, annotations);

                moveAhead();
                int nextPrec = getTokPrecedence();
                if (tokPrec < nextPrec)
                    rhs = parseBinOpRHS(identifier, annotations, nextPrec, rhs);

                if (binOp.type == Token.Type.TK_ACCESS)
                    lhs = new AccessExpression(lhs, rhs, line);
                else lhs = new OperationExpression(lhs, rhs, Operation.get(binOp.type), line);
            }
        }
    }

    public Script parseScript() throws ParserException, LexerException {
        List<Expression> children = new ArrayList<>();

        while (moveAhead().type != Token.Type.CLOSE_BRACKET) {
            children.add(readTopExpression());

            if (!lexer.isFinished()&&moveAhead().type!=Token.Type.TK_SEMI)
                throw new ParserException(lexer,
                        "A semicolon was expected, but it wasn't there. Please put it on!");
        }

        return new Script(new Container(children, 0));
    }

    public List<Expression> parseExpressions() throws ParserException, LexerException {
        if(lexer.currentToken().type!=Token.Type.OPEN_BRACKET)
            throw new ParserException(lexer, "Expected open bracket");

        List<Expression> expressions = new ArrayList<>();

        while (moveAhead().type != Token.Type.CLOSE_BRACKET) {
            expressions.add(readExpression());

            boolean closeBracket = lexer.currentToken().type == Token.Type.CLOSE_BRACKET;

            if (!closeBracket&&moveAhead().type!=Token.Type.TK_SEMI)
                throw new ParserException(lexer,
                        "A semicolon was expected, but it wasn't there. Please put it on!");
        }

        return expressions;
    }
}
