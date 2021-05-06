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

        TK_NAMESPACE_DELIMITER("\\\\"),
        TK_REF ("->"),
        TK_ANNOTATION ("\\@"),
        TK_NEW_LINE ("\\R"),
        TK_COMPANION_ACCESS ("::"),
        TK_COLON (":"),
        TK_OPEN ("\\("),
        TK_CLOSE ("\\)"),
        TK_SEMI (";"),
        TK_COMMA (","),
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

        //Short operations
        TK_PLUS_ONE ("\\+\\+"),
        TK_MINUS_ONE ("\\-\\-"),

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

        public int endOfMatch(String s) {
            Matcher m = pattern.matcher(s);

            if (m.find()) {
                return m.end();
            }
            return -1;
        }
    }
}

