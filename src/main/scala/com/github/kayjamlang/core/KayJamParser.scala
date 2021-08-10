package com.github.kayjamlang.core

import com.github.kayjamlang.core.KayJamIdentifier.{CLASS, FUNCTION}
import com.github.kayjamlang.core.KayJamParser.binOperationPrecedence
import com.github.kayjamlang.core.Token.Type.{BOOL, IDENTIFIER, INTEGER, LONG, NULL, OPEN_BRACKET, REAL, STRING, TK_ASSIGN, TK_NAMESPACE_DELIMITER, TK_SEMI}
import com.github.kayjamlang.core.containers._
import com.github.kayjamlang.core.exceptions.{LexerException, ParserException}
import com.github.kayjamlang.core.expressions.data.{Annotation, Argument, Operation}
import com.github.kayjamlang.core.expressions.loops.{ForExpression, WhileExpression}
import com.github.kayjamlang.core.expressions.{Expression, _}
import com.github.kayjamlang.core.opcodes.AccessType
import com.github.kayjamlang.core.stmts.{FunctionStmt, Stmt, StmtExpression}

import scala.collection.mutable
import scala.util.control.Breaks.break
import scala.util.control.ControlThrowable

class KayJamParser(val lexer: KayJamLexer) {

    @throws[ParserException]
    def parseStmt(): Stmt = {
        lexer.currentToken.`type` match {
            case IDENTIFIER => KayJamIdentifier.find(lexer.currentToken.value) match {
                case FUNCTION => parseFunction()
                case _ =>
                    val stmt = new StmtExpression(readTopExpression)
                    requireToken(TK_SEMI)
                    stmt
            }
            case _ => new StmtExpression(readTopExpression)
        }
    }

    @throws[ParserException]
    private def parseFunction(): FunctionStmt = {
        val name = requireToken(Token.Type IDENTIFIER).value
        requireToken(Token.Type TK_OPEN)
        val arguments = parseArguments
        var returnType = Type.VOID

        if(moveAhead.`type` eq Token.Type.TK_COLON) {
            requireToken(Token.Type IDENTIFIER)
            returnType = parseType(true)
        }

        val line = lexer.line
        new FunctionStmt(name, arguments, lexer.currentToken.`type` match {
            case OPEN_BRACKET => readTopExpression
            case TK_ASSIGN => new ReturnExpression(readTopExpression, line)
            case _ => throw new ParserException(this.lexer, "")
        }, returnType, AccessType NONE, new ArrayList[Annotation])
    }

    private def parseValue(): ValueExpression = lexer.currentToken.`type` match {
        case STRING => new ValueExpression(lexer.currentToken.value, Type.STRING)
        case INTEGER => new ValueExpression(lexer.currentToken.value.toInt, Type.INTEGER)
        case LONG => new ValueExpression(lexer.currentToken.value.toLong, Type.LONG)
        case REAL => new ValueExpression(lexer.currentToken.value.toDouble, Type.DOUBLE)
        case BOOL => new ValueExpression(lexer.currentToken.value.equals("true"), Type.BOOLEAN)
        case NULL => new ValueExpression(null, Type.NOTHING)
        case _ => null
    }

    @throws[LexerException]
    @throws[ParserException]
    def readExpression: Expression = readExpression(AccessType NONE, new ArrayList[Annotation])

    @throws[LexerException]
    @throws[ParserException]
    def readTopExpression: Expression = readTopExpression(AccessType NONE, new ArrayList[Annotation])

    @throws[LexerException]
    @throws[ParserException]
    def readTopExpression(identifier: AccessType, annotations: ArrayList[Annotation]): Expression = {
        var expression = readPrimary(identifier, annotations)
        if(currentTokenType eq Token.Type.CLOSE_BRACKET)
            return expression

        moveAhead
        expression = readEndExpression(expression)
        expression = parseBinOpRHS(identifier, annotations, 0, expression)
        moveAhead

        while(currentTokenType eq Token.Type.TK_OPEN_SQUARE_BRACKET) {
            val line = lexer.getLine
            moveAhead
            expression = new GetExpression(expression, readExpression, line)
            requireToken(Token.Type TK_CLOSE_SQUARE_BRACKET)
            moveAhead
            expression = readEndExpression(expression)
        }
        expression = parseBinOpRHS(identifier, annotations, 0, expression)
        expression
    }

    @throws[LexerException]
    @throws[ParserException]
    def readExpression(identifier: AccessType, annotations: ArrayList[Annotation]): Expression = {
        val expression = readTopExpression(identifier, annotations)
        if(expression.isInstanceOf[ClassContainer] || expression.isInstanceOf[UseExpression] || expression.isInstanceOf[PackContainer] || expression.isInstanceOf[ConstantValueExpression])
            throw new ParserException(lexer, "This expression is not allowed to be used in this place.")
        expression
    }

    @throws[LexerException]
    def readEndExpression(root: Expression): Expression = currentTokenType match {
        case Token.Type.TK_NOT =>
            moveAhead
            new AssertNullExpression(root, lexer getLine)
        case Token.Type.TK_PLUS_ONE =>
            moveAhead
            new OperationExpression(root, new ValueExpression(1, Type.INTEGER), Operation PLUS, lexer getLine)
        case Token.Type.TK_MINUS_ONE =>
            moveAhead
            new OperationExpression(root, new ValueExpression(1, Type.INTEGER), Operation MINUS, lexer getLine)
        case _ => root
    }

    def currentTokenType: Token.Type = lexer.currentToken.`type`

    @throws[LexerException]
    def moveAhead(needToken: Token.Type = null): Token = {
        lexer moveAhead needToken
        if(!lexer.isSuccessful) throw new ParserException(lexer errorMessage)
        lexer currentToken
    }

    @throws[LexerException]
    def moveAhead: Token = moveAhead(null)

    @throws[LexerException]
    @throws[ParserException]
    def requireToken(`type`: Token.Type): Token = {
        val token = moveAhead(`type`)
        if(token.`type` ne `type`) throw new ParserException(lexer, s"expected ${`type`.name.toLowerCase}")
        lexer.currentToken
    }

    @throws[LexerException]
    @throws[ParserException]
    def requireIdentifier(identifier: KayJamIdentifier): Unit = {
        val token = requireToken(Token.Type.IDENTIFIER)
        if(KayJamIdentifier.find(token.value) ne identifier) throw new ParserException(lexer, s"expected ${identifier.name}")
    }


    @throws[LexerException]
    @throws[ParserException]
    def readPrimary(identifier: AccessType, annotations: ArrayList[Annotation]): Expression = {
        var tokenType = currentTokenType
        val line = lexer.getLine
        tokenType match {
            case Token.Type.IDENTIFIER|TK_NAMESPACE_DELIMITER =>
                val keyword = KayJamIdentifier find lexer.currentToken.value
                if(tokenType!=TK_NAMESPACE_DELIMITER&&keyword!= null) keyword match {
                    case KayJamIdentifier.VAR =>
                        val name = requireToken(Token.Type IDENTIFIER).value
                        var variableType: Type = null

                        val token = moveAhead(Token.Type.TK_ASSIGN)
                        if(token.`type`==Token.Type.TK_COLON){
                            moveAhead
                            variableType = parseType(false)
                        }

                        moveAhead
                        val expression = readExpression
                        new VariableExpression(name, expression, variableType, identifier, line)
                        
                    case KayJamIdentifier.PRIVATE =>
                        if(identifier ne AccessType.NONE)
                            throw new ParserException(lexer, "Access type is already set")

                        moveAhead
                        readExpression(AccessType PRIVATE, annotations)

                    case KayJamIdentifier.PUBLIC =>
                        if(identifier ne AccessType.NONE)
                            throw new ParserException(lexer, "Access type is already set")

                        moveAhead
                        readExpression(AccessType PUBLIC, annotations)

                    case KayJamIdentifier.WHILE =>
                        requireToken(Token.Type TK_OPEN)
                        moveAhead

                        val condition = readExpression
                        if(currentTokenType ne Token.Type.TK_CLOSE)
                            throw new ParserException(lexer, "expected close \")\"")
                        else moveAhead

                        moveAhead
                        new WhileExpression(condition, readExpression, line)

                    case KayJamIdentifier.FOR =>
                        requireToken(Token.Type TK_OPEN)

                        val name = requireToken(Token.Type IDENTIFIER).value
                        requireIdentifier(KayJamIdentifier IN)

                        moveAhead
                        val range = readExpression
                        if(currentTokenType ne Token.Type.TK_CLOSE)
                            throw new ParserException(lexer, "expected close \")\"")
                        else moveAhead

                        moveAhead
                        new ForExpression(name, range, readExpression, line)

                    case KayJamIdentifier.OBJECT =>
                        val t = moveAhead
                        if(t.`type` eq Token.Type.OPEN_BRACKET)
                            new ObjectContainer(parseExpressions, identifier, line)
                        else if(t.`type` eq Token.Type.IDENTIFIER) {
                            moveAhead
                            new ObjectContainer(t.value, parseExpressions, identifier, line)
                        } else throw new ParserException(lexer, "expected name of object or open bracket")

                    case KayJamIdentifier.CLASS =>
                        moveAhead
                        tokenType = lexer.currentToken.`type`
                        if(tokenType eq Token.Type.IDENTIFIER) {
                            val name = lexer.currentToken.value
                            var extendsClass: String = null
                            val implementsClass = new ArrayList[String]
                            while(moveAhead.`type` ne Token.Type.OPEN_BRACKET)
                                if(currentTokenType eq Token.Type.TK_COMPANION_ACCESS)
                                    implementsClass += requireToken(Token.Type IDENTIFIER).value
                                else if((currentTokenType eq Token.Type.TK_COLON) && extendsClass == null)
                                    extendsClass = requireToken(Token.Type IDENTIFIER).value
                                else throw new ParserException(lexer, "expected open bracket or extends/implements token")
                            new ClassContainer(name, extendsClass, implementsClass, parseExpressions, identifier, line)
                        }
                        else throw new ParserException(lexer, "expected identifier of class")

                    case KayJamIdentifier.RETURN =>
                        if(moveAhead.value == "void") return new ReturnExpression(null, line)
                        new ReturnExpression(readExpression, line)

                    case KayJamIdentifier.CONSTRUCTOR =>
                        requireToken(Token.Type TK_OPEN)
                        val arguments = parseArguments
                        moveAhead
                        new ConstructorContainer(arguments, parseExpressions, identifier, line)

                    case KayJamIdentifier.USE =>
                        moveAhead
                        val needed = parseRequiredUsages("")
                        var from: String = null
                        if(lexer.currentToken.value == "from") {
                            from = requireToken(Token.Type STRING).value
                        }

                        new UseExpression(needed, from, line)

                    case KayJamIdentifier.COMPANION =>
                        moveAhead
                        readExpression(AccessType.COMPANION, annotations)

                    case KayJamIdentifier.IF =>
                        requireToken(Token.Type TK_OPEN)
                        moveAhead

                        val condition = readExpression
                        requireToken(Token.Type TK_CLOSE)
                        moveAhead

                        val ifTrue = readExpression
                        var ifFalse: Expression = null

                        if(KayJamIdentifier find moveAhead.value eq KayJamIdentifier.ELSE) {
                            moveAhead
                            ifFalse = readExpression
                        } else {
                            lexer.input = new StringBuilder(s"}${lexer.currentToken.value + lexer.input}")
                            moveAhead
                        }

                        new IfExpression(condition, ifTrue, ifFalse, line)

                    case KayJamIdentifier.PACK =>
                        moveAhead
                        val name = parseName
                        if(lexer.currentToken.`type` ne Token.Type.OPEN_BRACKET)
                            throw new ParserException(lexer, "Expected open bracket")
                        val expressions = new ArrayList[Expression]
                        while(moveAhead.`type` ne Token.Type.CLOSE_BRACKET) {
                            expressions += readTopExpression
                            val closeBracket = lexer.currentToken.`type` eq Token.Type.CLOSE_BRACKET
                            if (!closeBracket && (moveAhead.`type` ne Token.Type.TK_SEMI))
                                throwSemicolon()
                        }
                        new PackContainer(name, new Container(expressions, line), false)

                    case KayJamIdentifier.CONSTANT =>
                        val name = requireToken(Token.Type IDENTIFIER).value
                        requireToken(Token.Type TK_ASSIGN)
                        moveAhead
                        val expression = readExpression
                        expression match {
                            case expression1: ValueExpression => new ConstantValueExpression(name, expression1, line)
                            case _ => throw new ParserException(expression.line, "Expression cannot be constant")
                        }

                    case _ => throw new ParserException(lexer, "\"" + lexer.currentToken.value + "\" is in the wrong place")
                } else {
                    val name = parseName
                    currentTokenType match {
                        case Token.Type.TK_ASSIGN =>
                            moveAhead
                            val expression = readExpression
                            new VariableSetExpression(name, expression, line)

                        case Token.Type.TK_OPEN =>
                            val arguments = new ArrayList[Expression]
                            try {
                                while(moveAhead.`type` ne Token.Type.TK_CLOSE) {
                                    arguments += readExpression
                                    val token = moveAhead
                                    if(token.`type` eq Token.Type.TK_CLOSE)
                                        break
                                    else if(token.`type` ne Token.Type.TK_COMMA)
                                        throw new ParserException(lexer, "expected comma \",\"")
                                }
                            } catch {
                                case _: ControlThrowable =>
                            }
                            new CallOrCreateExpression(name, arguments, line)

                        case Token.Type.TK_ACCESS =>
                            moveAhead
                            new AccessExpression(new VariableLinkExpression(name, line), readExpression, line)
                        case Token.Type.TK_COMPANION_ACCESS =>
                            moveAhead
                            new CompanionAccessExpression(name, readExpression, line)
                        case Token.Type.TK_REF =>
                            if(tokenType eq Token.Type.TK_REF) moveAhead
                            new NamedExpression(name, readExpression, line)
                        case Token.Type.OPEN_BRACKET =>
                            if(tokenType eq Token.Type.TK_REF) moveAhead
                            new NamedExpression(name, readExpression, line)
                        case _ =>
                            lexer.input = new StringBuilder(lexer.currentToken.value + lexer.input)
                            new VariableLinkExpression(name, line)
                    }
                }

            case Token.Type.TK_ANNOTATION =>
                val name = requireToken(Token.Type IDENTIFIER).value
                moveAhead
                if(currentTokenType eq Token.Type.TK_OPEN) {
                    moveAhead
                    val value = readExpression
                    if(!value.isInstanceOf[ValueExpression])
                        throw new ParserException(lexer, "Invalid value of annotation")
                    annotations += new Annotation(name, value.asInstanceOf[ValueExpression])
                    requireToken(Token.Type TK_CLOSE)
                    moveAhead
                }
                else annotations += new Annotation(name)
                readExpression(identifier, annotations)

            case Token.Type.TK_OPEN =>
                moveAhead
                val expression = readExpression
                requireToken(Token.Type TK_CLOSE)
                expression

            case Token.Type.TK_REF =>
                var x = false // TODO: please rename "x" variable
                var x1 = false // TODO: please rename "x1" variable

                var arguments = new ArrayList[Argument]
                if(moveAhead.`type` eq Token.Type.TK_OPEN) {
                    arguments = parseArguments
                    moveAhead
                    x1 = true
                    lexer.input = new StringBuilder(s"{ ${lexer.currentToken.value + lexer.input} }")
                } else {
                    val value = lexer.currentToken.value
                    if (value.apply(0) == '{')
                        lexer.input = new StringBuilder(lexer.currentToken.value + lexer.input)
                    else {
                        x = true
                        lexer.input = new StringBuilder(s"{ ${value + lexer.input} }")
                    }
                }

                var returnType = Type.VOID
                if(moveAhead.`type` eq Token.Type.TK_COLON) {
                    requireToken(Token.Type IDENTIFIER)
                    returnType = parseType(true)
                } else
                    lexer.input = new StringBuilder(lexer.currentToken.value + lexer.input)

                val expr: Expression = readExpression
                expr match {
                    case expression: NamedExpression =>
                        new FunctionRefExpression(arguments, expression.expression.asInstanceOf[Container].children.head, returnType, line)

                    case expression: Container =>
                        if (x)
                            new FunctionRefExpression(arguments, expression.children.head.asInstanceOf[Container].children.head, returnType, line)
                        else {
                            if (x1)
                                new FunctionRefExpression(arguments, expression.children.head.asInstanceOf[Container].children.head, returnType, line)
                            else
                                new FunctionRefExpression(arguments, expression.children.head, returnType, line)
                        }

                    case _ =>
                        new FunctionRefExpression(arguments, expr, returnType, line)
                }

            case Token.Type.TK_NOT =>
                moveAhead
                new NegationExpression(readExpression(identifier, annotations), line)
            case Token.Type.OPEN_BRACKET =>
                new Container(parseExpressions, AccessType PUBLIC, line)
            case Token.Type.TK_OPEN_SQUARE_BRACKET =>
                val values = new ArrayList[Expression]
                try {
                    while(moveAhead.`type` ne Token.Type.TK_CLOSE_SQUARE_BRACKET) {
                        values += readExpression(identifier, annotations)
                        val token = moveAhead
                        if(token.`type` eq Token.Type.TK_CLOSE_SQUARE_BRACKET) {
                            moveAhead
                            break
                        } else if(token.`type` ne Token.Type.TK_COMMA)
                            throw new ParserException(line, "expected comma")
                    }
                } catch {
                    case _: ControlThrowable =>
                }
                new ArrayExpression(values, line)
            case Token.Type.TK_SEMI =>
                moveAhead
                readPrimary(identifier, annotations)
            case Token.Type.TK_MINUS =>
                moveAhead
                new OperationExpression(new ValueExpression(-1, Type.INTEGER), readExpression(identifier, annotations), Operation MULTIPLY, line)
            case _ =>
                val value = parseValue()
                value match {
                    case null => throw new ParserException(lexer, "\"" + lexer.currentToken.value + "\" is in the wrong place")
                    case _ => value
                }
        }
    }

    @throws[LexerException]
    @throws[ParserException]
    def parseRequiredUsages(root: String, x: Boolean = false): ArrayList[String] = { // TODO: please rename "x" variable
        val usages = new ArrayList[String]
        if (currentTokenType eq Token.Type.OPEN_BRACKET) {
            try {
                while (moveAhead.`type` ne Token.Type.CLOSE_BRACKET) {
                    for (e <- parseRequiredUsages(root, x = true))
                        usages += e
                    if (currentTokenType eq Token.Type.CLOSE_BRACKET)
                        break
                    else if (currentTokenType ne Token.Type.TK_COMMA)
                        throw new ParserException(lexer, "excepted comma")
                }
            } catch {
                case _: ControlThrowable =>
            }
            moveAhead
        } else {
            val name = if (x && root.nonEmpty)
                s"$root\\$parseName"
            else s"$root$parseName"
            if (currentTokenType eq Token.Type.OPEN_BRACKET) {
                for (e <- parseRequiredUsages(name, x = true))
                    usages += e
            } else
                usages += name
        }

        usages
    }

    @throws[LexerException]
    @throws[ParserException]
    def parseName: String = {
        if(currentTokenType eq Token.Type.TK_NAMESPACE_DELIMITER)
            moveAhead

        if(currentTokenType ne Token.Type.IDENTIFIER)
            throw new ParserException(lexer, "excepted type")

        val name = new StringBuilder(lexer.currentToken.value)
        while( {
            moveAhead.`type` eq Token.Type.TK_NAMESPACE_DELIMITER
        }) name.append("\\").append(moveAhead.value)

        name.toString
    }

    def getTokPrecedence: Int = {
        if(!binOperationPrecedence.keys.exists(_ == lexer.currentToken.value)) { //lexer.input = new StringBuilder(lexer.currentToken().value+" "+lexer.input);
            return -1
        }

        val tokPrec = binOperationPrecedence get lexer.currentToken.value
        if(tokPrec.get < 0)
            return -1

        tokPrec.get
    }

    @throws[LexerException]
    @throws[ParserException]
    def parseType(isFunc: Boolean): Type = {
        val `type` = Type.getType(parseName, isFunc)
        if(currentTokenType eq Token.Type.TK_NULLABLE) {
            if(`type` == Type.VOID)
                throw new ParserException(lexer, "Void cannot be nullable")
            `type`.nullable = true
            moveAhead
        }
        `type`
    }

    @throws[LexerException]
    @throws[ParserException]
    def parseArguments: ArrayList[Argument] = {
        val arguments = new ArrayList[Argument]
        try {
            while(true) {
                moveAhead
                if(lexer.currentToken.`type` eq Token.Type.TK_CLOSE)
                    break
                val name = lexer.currentToken.value
                val argType = parseType(false)
                var t = lexer.currentToken
                if(t.`type` eq Token.Type.IDENTIFIER) {
                    arguments += new Argument(argType, t.value)
                    t = moveAhead
                }
                else if((t.`type` eq Token.Type.TK_COMMA) || (t.`type` eq Token.Type.TK_CLOSE))
                    arguments += new Argument(Type ANY, name)
                if(t.`type` eq Token.Type.TK_CLOSE)
                    break
                else if(t.`type` ne Token.Type.TK_COMMA)
                    throw new ParserException(lexer, "expected comma \",\"")
            }
        } catch {
            case _: ControlThrowable =>
        }
        arguments
    }

    @throws[LexerException]
    @throws[ParserException]
    def parseBinOpRHS(identifier: AccessType, annotations: ArrayList[Annotation], exprPrec: Int, lhsA: Expression): Expression = {
        var lhs = lhsA
        while(true) {
            val tokPrec = getTokPrecedence
            if(tokPrec < exprPrec) {
                lexer.input = new StringBuilder(s"${lexer.currentToken.value} ${lexer.input}")
                return lhs
            }
            val binOp = lexer.currentToken
            val kayJamIdentifier = KayJamIdentifier.find(binOp.value)
            val line = lexer.getLine
            if(kayJamIdentifier eq KayJamIdentifier.CAST) {
                moveAhead
                lhs = new CastExpression(lhs, parseType(false), line)
            }
            else if(kayJamIdentifier eq KayJamIdentifier.IS) {
                moveAhead
                lhs = new IsExpression(lhs, parseType(false), line)
            }
            else {
                moveAhead
                var rhs = readPrimary(identifier, annotations)
                moveAhead
                if(binOp.`type` eq Token.Type.TK_ACCESS)
                    lhs = new AccessExpression(lhs, rhs, line)
                else if(binOp.`type` eq Token.Type.TK_RANGE)
                    lhs = new OperationExpression(lhs, rhs, Operation RANGE, line)
                else {
                    val nextPrec = getTokPrecedence
                    if(tokPrec < nextPrec)
                        rhs = parseBinOpRHS(identifier, annotations, nextPrec, rhs)
                    lhs = new OperationExpression(lhs, rhs, Operation.get(binOp.`type`), line)
                }
            }
        }
        lhs
    }

    @throws[ParserException]
    @throws[LexerException]
    def parseScript: Script = {
        val children = new ArrayList[Expression]
        while(!lexer.isFinished) {
            children += readTopExpression
            val closeBracket = lexer.currentToken.`type` eq Token.Type.CLOSE_BRACKET
            if(!closeBracket && !lexer.isFinished && (moveAhead.`type` ne Token.Type.TK_SEMI))
                throwSemicolon()
            moveAhead
        }
        new Script(new Container(children.asInstanceOf, 0))
    }

    @throws[ParserException]
    @throws[LexerException]
    def parseExpressions: ArrayList[Expression] = {
        if(lexer.currentToken.`type` ne Token.Type.OPEN_BRACKET)
            throw new ParserException(lexer, "Expected open bracket")

        val expressions = new ArrayList[Expression]
        while(moveAhead.`type` ne Token.Type.CLOSE_BRACKET) {
            expressions += readExpression
            val closeBracket = lexer.currentToken.`type` eq Token.Type.CLOSE_BRACKET
            if(!closeBracket && (moveAhead.`type` ne Token.Type.TK_SEMI))
                throwSemicolon()
        }
        expressions
    }

    @throws[ParserException]
    private[core] def throwSemicolon(): Unit = {
        throw new ParserException(lexer, "A semicolon was expected, but it wasn't there. Please put it on!")
    }
}

object KayJamParser {
    val binOperationPrecedence = new mutable.HashMap[String, Integer]

    binOperationPrecedence put("as", 0)
    binOperationPrecedence put("is", 0)
    binOperationPrecedence put("<", 10)
    binOperationPrecedence put("=<", 10)
    binOperationPrecedence put(">", 10)
    binOperationPrecedence put(">=", 10)
    binOperationPrecedence put("+", 20)
    binOperationPrecedence put("-", 20)
    binOperationPrecedence put("==", 20)
    binOperationPrecedence put("!=", 20)
    binOperationPrecedence put("*", 40)
    binOperationPrecedence put("/", 40)
    binOperationPrecedence put("..", 50)
    binOperationPrecedence put(".", 50)
    binOperationPrecedence put("&&", 70)
    binOperationPrecedence put("||", 70)
}
