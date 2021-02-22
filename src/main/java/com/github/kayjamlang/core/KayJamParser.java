package com.github.kayjamlang.core;

import com.github.kayjam.core.containers.*;
import com.github.kayjamlang.core.containers.*;
import com.github.kayjamlang.core.exceptions.LexerException;
import com.github.kayjam.core.expressions.*;
import com.github.kayjamlang.core.exceptions.CompileException;
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
        binOperationPrecedence.put(">", 10);
        binOperationPrecedence.put("+", 20);
        binOperationPrecedence.put("-", 20);
        binOperationPrecedence.put("==", 20);
        binOperationPrecedence.put("!=", 20);
        binOperationPrecedence.put("&&", 30);
        binOperationPrecedence.put("||", 30);
        binOperationPrecedence.put("*", 40);
        binOperationPrecedence.put("/", 40);
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

        if (currentTokenType() == Token.Type.TK_OPEN_SQUARE_BRACKET)
            moveAhead();

        while(currentTokenType() == Token.Type.TK_OPEN_SQUARE_BRACKET){
            if(expression instanceof ArrayGet)
                moveAhead();

            int line = lexer.getLine();

            moveAhead();
            expression = new ArrayGet(expression, readExpression(), line);
            requireToken(Token.Type.TK_CLOSE_SQUARE_BRACKET);
        }

        moveAhead();
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

    public Token requireToken(Token.Type type) throws LexerException, CompileException {
        Token token = moveAhead();
        if(token.type!=type)
            throw new CompileException(lexer, "expected "+type.name().toLowerCase());

        return lexer.currentToken();
    }

    public Expression readPrimary(AccessIdentifier identifier, List<Annotation> annotations) throws Exception {
        Token.Type type = currentTokenType();

        if(type==Token.Type.TK_VAR){
            String name = requireToken(Token.Type.IDENTIFIER).value;
            requireToken(Token.Type.TK_ASSIGN);

            moveAhead();
            Expression expression = readExpression();

            return new Variable(name, expression, identifier, lexer.getLine());
        }else if(type == Token.Type.IDENTIFIER){
            String name = lexer.currentToken().value;

            moveAhead();
            type = lexer.currentToken().type;
            if(type==Token.Type.TK_ASSIGN) {
                moveAhead();
                Expression expression = readExpression();
                return new VariableSet(name, expression, lexer.getLine());
            }else if(type==Token.Type.TK_OPEN){
                List<Expression> arguments = new ArrayList<>();
                while (moveAhead().type!=Token.Type.TK_CLOSE){
                    arguments.add(readExpression());

                    Token token = moveAhead();
                    if(token.type==Token.Type.TK_CLOSE)
                        break;
                    else if(token.type!=Token.Type.TK_COMMA)
                        throw new CompileException(lexer, "expected comma \",\"");
                }

                return new CallCreate(name, arguments, lexer.getLine());
            }else if(type==Token.Type.TK_ACCESS){
                moveAhead();
                return new Access(new VariableLink(name, lexer.getLine()), readExpression(), lexer.getLine());
            }else if(type==Token.Type.TK_COMPANION_ACCESS){
                moveAhead();
                return new CompanionAccess(name, readExpression(), lexer.getLine());
            }else{
                lexer.input = new StringBuilder(lexer.currentToken().value+lexer.input);
                return new VariableLink(name, lexer.getLine());
            }
        }else if(type == Token.Type.TK_FUNCTION){
            String name = requireToken(Token.Type.IDENTIFIER).value;

            requireToken(Token.Type.TK_OPEN);

            List<Function.Argument> arguments = new ArrayList<>();
            while (true) {
                moveAhead();
                if (lexer.currentToken().type == Token.Type.TK_CLOSE)
                    break;

                Type argType = Type.getType(lexer.currentToken().value);
                arguments.add(new Function.Argument(argType,
                        requireToken(Token.Type.IDENTIFIER).value));

                Token token = moveAhead();
                if (token.type == Token.Type.TK_CLOSE)
                    break;
                else if (token.type != Token.Type.TK_COMMA)
                    throw new CompileException(lexer, "expected comma \",\"");
            }

            Type returnType = Type.VOID;
            if(moveAhead().type==Token.Type.TK_COLON){
                requireToken(Token.Type.IDENTIFIER);

                returnType = Type.getType(lexer.currentToken().value, true);
                moveAhead();
            }

            List<Expression> body = parseAST();

            return new Function(name, body, identifier, arguments, lexer.getLine(), returnType, annotations);
        }else if(type == Token.Type.TK_THREAD){
            moveAhead();
            return new ThreadContainer(parseAST(), lexer.getLine());
        }else if(type == Token.Type.TK_OBJECT){
            moveAhead();
            return new ObjectContainer(parseAST(), identifier, lexer.getLine());
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
                    else throw new CompileException(lexer, "expected open bracket or extends/implements token");
                }

                return new ClassContainer(name, extendsClass, implementsClass,
                        parseAST(), identifier, lexer.getLine());
            }else throw new CompileException(lexer, "expected identifier of class");
        }else if(type == Token.Type.TK_RETURN){
            moveAhead();
            return new Return(readExpression(), lexer.getLine());
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

            return new If(condition, ifTrue, ifFalse, lexer.getLine());
        }else if(type == Token.Type.TK_ANNOTATION){
            String name = requireToken(Token.Type.IDENTIFIER).value;

            moveAhead();
            if(currentTokenType()==Token.Type.TK_OPEN){

                moveAhead();
                Expression value = readExpression();
                if(!(value instanceof Const))
                    throw new CompileException(lexer, "Invalid value of annotation");

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
            List<String> arguments = new ArrayList<>();
            if(moveAhead().type==Token.Type.TK_OPEN) {
                arguments = parseArguments();
                moveAhead();
            }

            return new FunctionRef(arguments, readExpression(), lexer.getLine());
        }else if(type == Token.Type.TK_NOT){
            int line = lexer.getLine();

            moveAhead();
            return new Not(readExpression(identifier, annotations), line);
        }else if(type == Token.Type.TK_CONSTRUCTOR){
            int line = lexer.getLine();

            requireToken(Token.Type.TK_OPEN);
            List<String> arguments = parseArguments();

            moveAhead();
            return new ConstructorContainer(arguments, parseAST(), identifier, line);
        }else if(type == Token.Type.OPEN_BRACKET){
            return new Container(parseAST(), AccessIdentifier.PUBLIC, lexer.getLine());
        }else if(type == Token.Type.TK_OPEN_SQUARE_BRACKET){
            int line = lexer.getLine();

            List<Expression> values = new ArrayList<>();
            while (moveAhead().type!=Token.Type.TK_CLOSE_SQUARE_BRACKET){
                values.add(readExpression(identifier, annotations));

                Token token = moveAhead();
                if(token.type==Token.Type.TK_CLOSE_SQUARE_BRACKET)
                    break;
                else if(token.type!=Token.Type.TK_COMMA)
                    throw new CompileException(lexer.getLine(), "expected comma");
            }

            return new Array(values, line);
        }else if(type == Token.Type.TK_USE){
            moveAhead();
            return new Use(readExpression(), lexer.getLine());
        }else if(type == Token.Type.STRING){
            return new Const(lexer.currentToken().value.substring(1, lexer.currentToken().value.length()-1), lexer.getLine());
        }else if(type == Token.Type.INTEGER){
            return new Const(Long.parseLong(lexer.currentToken().value), lexer.getLine());
        }else if(type == Token.Type.REAL){
            return new Const(Double.parseDouble(lexer.currentToken().value), lexer.getLine());
        }else if(type == Token.Type.BOOL){
            return new Const(lexer.currentToken().value.equals("true"), lexer.getLine());
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
            int line = lexer.getLine();

            moveAhead();
            return new OperationExpression(new Const(-1, line), readPrimary(identifier, annotations),
                    Operation.MULTIPLY, line);
        }

        throw new CompileException(lexer, "\""+lexer.currentToken().value+"\" is in the wrong place");
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

    public List<String> parseArguments() throws LexerException, CompileException {
        List<String> arguments = new ArrayList<>();
        while (true) {
            moveAhead();
            if (lexer.currentToken().type == Token.Type.TK_CLOSE)
                break;

            arguments.add(lexer.currentToken().value);

            Token token = moveAhead();
            if (token.type == Token.Type.TK_CLOSE)
                break;
            else if (token.type != Token.Type.TK_COMMA)
                throw new CompileException(lexer, "expected comma \",\"");
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

            moveAhead();
            Expression rhs = readPrimary(identifier, annotations);

            moveAhead();
            int nextPrec = getTokPrecedence();
            if(tokPrec<nextPrec){
                rhs = parseBinOpRHS(identifier, annotations, nextPrec, rhs);
            }//else moveAhead();

            lhs = new OperationExpression(lhs, rhs, binOp, line);
        }
    }

    public List<Expression> parseAST() throws Exception {
        if(lexer.currentToken().type!=Token.Type.OPEN_BRACKET)
            throw new CompileException(lexer, "Expected open bracket");

        List<Expression> expressions = new ArrayList<>();

        while (moveAhead().type != Token.Type.CLOSE_BRACKET) {
            expressions.add(readExpression());

            boolean closeBracket = lexer.currentToken().type == Token.Type.CLOSE_BRACKET;

            if (!closeBracket&&moveAhead().type!=Token.Type.TK_SEMI)
                throw new CompileException(lexer,
                        "A semicolon was expected, but it wasn't there. Please put it on!");
        }

        return expressions;
    }
}
