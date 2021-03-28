package com.github.kayjamlang.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Token {

    public final Token.Type type;
    public final String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public enum Type {
        COMMENT ("//(.+)"),

        TK_REF ("->"),
        TK_ANNOTATION ("\\@"),
        TK_NEW_LINE ("\\R"),
        TK_PUBLIC ("public "),
        TK_PRIVATE ("private "),
        TK_COMPANION ("companion "),
        TK_RETURN ("return "),
        TK_CONSTRUCTOR ("constructor"),
        TK_FUNCTION ("(function|fun)"),
        TK_CLASS ("class"),
        TK_OBJECT ("object"),
        TK_USE ("use "),
        TK_WHILE ("while"),
        TK_NAMED ("named "),
        TK_FOR ("for"),
        TK_COMPANION_ACCESS ("::"),
        TK_COLON (":"),
        TK_OPEN ("\\("),
        TK_CLOSE ("\\)"),
        TK_SEMI (";"),
        TK_COMMA (","),
        TK_VAR ("var"),
        TK_AS("as"),
        TK_IS("is"),
        TK_KEY_IN ("in "),
        TK_KEY_IF ("if"),
        TK_KEY_ELSE ("else"),
        TK_NULLABLE ("\\?"),
        TK_OPEN_SQUARE_BRACKET("\\["),
        TK_CLOSE_SQUARE_BRACKET("\\]"),
        OPEN_BRACKET ("\\{"),
        CLOSE_BRACKET ("\\}"),
        DIFFERENT ("<>"),

        BOOL ("(true|false)"),
        NULL ("null"),
        STRING ("(\"[^\"]+\"|\"\")"),
        REAL ("(\\d*)\\.\\d+"),
        LONG ("\\d+[lL]"),
        INTEGER ("\\d+"),
        IDENTIFIER ("\\w+"),

        //Binary operations
        TK_MINUS ("-"),
        TK_PLUS ("\\+"),
        TK_MUL ("\\*"),
        TK_DIV ("/"),

        TK_AND ("&&"),
        TK_OR ("\\|\\|"),
        TK_EQUALS ("=="),
        TK_NOT_EQUALS ("!="),

        TK_LESS_SIGN ("<"),
        TK_LESS_EQUALS_SIGN ("<="),
        TK_GREATER_SIGN (">"),
        TK_GREATER_EQUALS_SIGN (">="),

        TK_RANGE ("\\.\\."),


        TK_NOT ("!"),
        TK_ASSIGN ("="),
        TK_ACCESS ("\\.");

        private final Pattern pattern;

        Type(String regex) {
            pattern = Pattern.compile("^" + regex);
        }

        int endOfMatch(String s) {
            Matcher m = pattern.matcher(s);

            if (m.find()) {
                return m.end();
            }
            return -1;
        }
    }
}

