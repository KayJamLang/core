package com.github.kayjamlang.core;

import com.github.kayjamlang.core.containers.*;
import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.exceptions.LexerException;
import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KayJamParser {

    private final KayJamLexer lexer;

    private static final Map<String, Integer> binOperationPrecedence;

    static {
        binOperationPrecedence = new HashMap<>();
        binOperationPrecedence.put("<", 10);
        binOperationPrecedence.put("=<", 10);
        binOperationPrecedence.put(">", 10);
        binOperationPrecedence.put(">=", 10);
        binOperationPrecedence.put("+", 20);
        binOperationPrecedence.put("-", 20);
        binOperationPrecedence.put("==", 20);
        binOperationPrecedence.put("!=", 20);
        binOperationPrecedence.put("&&", 30);
        binOperationPrecedence.put("*", 40);
        binOperationPrecedence.put("/", 40);
        binOperationPrecedence.put("..", 50);
        binOperationPrecedence.put(".", 60);
        binOperationPrecedence.put("as", 60);
        binOperationPrecedence.put("is", 60);
        binOperationPrecedence.put("||", 70);
    }

    public KayJamParser(KayJamLexer lexer) {
        this.lexer = lexer;
    }

    public Expression readExpression() throws Exception {
        return readExpression(AccessIdentifier.NONE, new ArrayList<>());
    }

    public Expression readExpression(AccessIdentifier identifier, List<Annotation> annotations)
            throws Exception {

        Expression expression = readPrimary(identifier, annotations);
        if(currentTokenType() == Token.Type.CLOSE_BRACKET)
            return expression;

        moveAhead();
        expression = parseBinOpRHS(identifier, annotations, 0, expression);

        moveAhead();
        while(currentTokenType() == Token.Type.TK_OPEN_SQUARE_BRACKET){
            int line = lexer.getLine();

            moveAhead();
            expression = new GetExpression(expression, readExpression(), line);
            requireToken(Token.Type.TK_CLOSE_SQUARE_BRACKET);

            moveAhead();
        }

        expression = parseBinOpRHS(identifier, annotations, 0, expression);
        return expression;
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

    public Expression readPrimary(AccessIdentifier identifier, List<Annotation> annotations) throws Exception {
        Token.Type type = currentTokenType();
        int line = lexer.getLine();

        if(type==Token.Type.TK_VAR){
            String name = requireToken(Token.Type.IDENTIFIER).value;
            requireToken(Token.Type.TK_ASSIGN);

            moveAhead();
            Expression expression = readExpression();

            return new Variable(name, expression, identifier, line);
        }else if(type == Token.Type.IDENTIFIER){
            String name = lexer.currentToken().value;

            type = moveAhead().type;
            if(type==Token.Type.TK_ASSIGN) {
                moveAhead();
                Expression expression = readExpression();
                return new VariableSet(name, expression, line);
            }else if(type==Token.Type.TK_OPEN){
                List<Expression> arguments = new ArrayList<>();
                while (moveAhead().type!=Token.Type.TK_CLOSE){
                    arguments.add(readExpression());

                    Token token = moveAhead();
                    if(token.type==Token.Type.TK_CLOSE)
                        break;
                    else if(token.type!=Token.Type.TK_COMMA)
                        throw new ParserException(lexer, "expected comma \",\"");
                }

                return new CallCreate(name, arguments, line);
            }else if(type==Token.Type.TK_ACCESS){
                moveAhead();
                return new Access(new VariableLink(name, line), readExpression(), line);
            }else if(type==Token.Type.TK_COMPANION_ACCESS){
                moveAhead();
                return new CompanionAccess(name, readExpression(), line);
            }else if(type==Token.Type.OPEN_BRACKET||type==Token.Type.TK_REF){
                if(type==Token.Type.TK_REF)
                    moveAhead();

                return new NamedExpression(name, readExpression(), line);
            }else{
                lexer.input = new StringBuilder(lexer.currentToken().value+lexer.input);
                return new VariableLink(name, line);
            }
        }else if(type == Token.Type.TK_NAMED){
            requireToken(Token.Type.TK_FUNCTION);
            String name = requireToken(Token.Type.IDENTIFIER).value;

            moveAhead();
            return new NamedExpressionFunction(name, parseAST(), identifier, line);
        }else if(type == Token.Type.TK_FUNCTION){
            String name = requireToken(Token.Type.IDENTIFIER).value;

            requireToken(Token.Type.TK_OPEN);

            List<Argument> arguments = parseArguments();

            Type returnType = Type.VOID;
            if(moveAhead().type==Token.Type.TK_COLON){
                requireToken(Token.Type.IDENTIFIER);

                returnType = parseType(true);
            }

            List<Expression> body = parseAST();

            return new Function(name, body, identifier, arguments, line, returnType, annotations);
        }else if(type == Token.Type.TK_WHILE){
            requireToken(Token.Type.TK_OPEN);

            moveAhead();
            Expression condition = readExpression();

            if(currentTokenType()!= Token.Type.TK_CLOSE)
                throw new ParserException(lexer, "expected close \")\"");
            else moveAhead();

            moveAhead();
            return new WhileExpression(condition, readExpression(), line);
        }else if(type == Token.Type.TK_FOR){
            requireToken(Token.Type.TK_OPEN);

            String name = requireToken(Token.Type.IDENTIFIER).value;
            requireToken(Token.Type.TK_KEY_IN);

            moveAhead();
            Expression range = readExpression();

            if(currentTokenType()!= Token.Type.TK_CLOSE)
                throw new ParserException(lexer, "expected close \")\"");
            else moveAhead();

            moveAhead();
            return new ForExpression(name, range, readExpression(), line);
        }else if(type == Token.Type.TK_OBJECT){
            Token t = moveAhead();
            if(t.type==Token.Type.OPEN_BRACKET)
                return new AnonymousObjectContainer(parseAST(), identifier, line);
            else if(t.type==Token.Type.IDENTIFIER){
                moveAhead();
                return new ObjectContainer(t.value,
                        parseAST(), identifier, line);
            }else throw new ParserException(lexer, "expected name of object or open bracket");
        }else if(type == Token.Type.TK_CLASS){
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
                        parseAST(), identifier, line);
            }else throw new ParserException(lexer, "expected identifier of class");
        }else if(type == Token.Type.TK_RETURN){
            moveAhead();
            return new Return(readExpression(), line);
        }else if(type == Token.Type.TK_KEY_IF){
            requireToken(Token.Type.TK_OPEN);

            moveAhead();
            Expression condition = readExpression();

            requireToken(Token.Type.TK_CLOSE);

            moveAhead();
            Expression ifTrue = readExpression();
            Expression ifFalse = null;

            if(moveAhead().type == Token.Type.TK_KEY_ELSE){
                moveAhead();
                ifFalse = readExpression();
            }else {
                lexer.input = new StringBuilder("}"+lexer.currentToken().value+lexer.input);
                moveAhead();
            }

            return new If(condition, ifTrue, ifFalse, line);
        }else if(type == Token.Type.TK_ANNOTATION){
            String name = requireToken(Token.Type.IDENTIFIER).value;

            moveAhead();
            if(currentTokenType()==Token.Type.TK_OPEN){

                moveAhead();
                Expression value = readExpression();
                if(!(value instanceof Const))
                    throw new ParserException(lexer, "Invalid value of annotation");

                annotations.add(new Annotation(name, (Const) value));
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

            return new FunctionRef(arguments, readExpression(), line);
        }else if(type == Token.Type.TK_NOT){
            moveAhead();
            return new Not(readExpression(identifier, annotations), line);
        }else if(type == Token.Type.TK_CONSTRUCTOR){
            requireToken(Token.Type.TK_OPEN);
            List<Argument> arguments = parseArguments();

            moveAhead();
            return new ConstructorContainer(arguments, parseAST(), identifier, line);
        }else if(type == Token.Type.OPEN_BRACKET){
            return new Container(parseAST(), AccessIdentifier.PUBLIC, line);
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

            return new Array(values, line);
        }else if(type == Token.Type.TK_USE){
            moveAhead();
            return new Use(readExpression(), line);
        }else if(type == Token.Type.STRING){
            return new Const(lexer.currentToken().value.substring(1, lexer.currentToken().value.length()-1), line);
        }else if(type == Token.Type.NULL){
            return new Const(null, line);
        }else if(type == Token.Type.LONG){
            return new Const(Long.parseLong(lexer.currentToken()
                    .value.substring(0, lexer.currentToken().value.length()-1)), line);
        }else if(type == Token.Type.INTEGER){
            return new Const(Integer.parseInt(lexer.currentToken().value), line);
        }else if(type == Token.Type.REAL){
            return new Const(Double.parseDouble(lexer.currentToken().value), line);
        }else if(type == Token.Type.BOOL){
            return new Const(lexer.currentToken().value.equals("true"), line);
        }else if(type == Token.Type.TK_PRIVATE) {
            moveAhead();
            return readExpression(AccessIdentifier.PRIVATE, annotations);
        }else if(type == Token.Type.TK_PUBLIC) {
            moveAhead();
            return readExpression(AccessIdentifier.PUBLIC, annotations);
        }else if(type == Token.Type.TK_COMPANION) {
            moveAhead();
            return readExpression(AccessIdentifier.COMPANION, annotations);
        }else if(type == Token.Type.TK_SEMI){
            moveAhead();
            return readPrimary(identifier, annotations);
        }else if(type == Token.Type.TK_MINUS){
            moveAhead();
            return new OperationExpression(new Const(-1, line), readExpression(identifier, annotations),
                    Operation.MULTIPLY, line);
        }

        throw new ParserException(lexer, "\""+lexer.currentToken().value+"\" is in the wrong place");
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
        if(lexer.currentToken().type != Token.Type.IDENTIFIER)
            throw new ParserException(lexer, "expected type identifier");

        Type type = Type.getType(lexer.currentToken().value, isFunc);
        if(moveAhead().type==Token.Type.TK_NULLABLE){
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

    public Expression parseBinOpRHS(AccessIdentifier identifier, List<Annotation> annotations,
                                    int exprPrec, Expression lhs) throws Exception {

        while (true) {
            int tokPrec = getTokPrecedence();

            if(tokPrec<exprPrec) {
                lexer.input = new StringBuilder(lexer.currentToken().value+" "+lexer.input);
                return lhs;
            }

            Token binOp = lexer.currentToken();
            int line = lexer.getLine();

            if(binOp.type==Token.Type.TK_AS) {
                moveAhead();
                lhs = new CastExpression(lhs, parseType(false), line);
            }else if(binOp.type==Token.Type.TK_IS) {
                moveAhead();
                lhs = new IsExpression(lhs, parseType(false), line);
            }else{
                moveAhead();
                Expression rhs = readPrimary(identifier, annotations);

                moveAhead();
                int nextPrec = getTokPrecedence();
                if (tokPrec < nextPrec) {
                    rhs = parseBinOpRHS(identifier, annotations, nextPrec, rhs);
                }//else moveAhead();

                if (binOp.type == Token.Type.TK_ACCESS)
                    lhs = new Access(lhs, rhs, line);
                else lhs = new OperationExpression(lhs, rhs, binOp, line);
            }
        }
    }

    public List<Expression> parseAST() throws Exception {
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
