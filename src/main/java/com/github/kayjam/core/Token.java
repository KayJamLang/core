package com.github.kayjam.core;

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
        TK_NEW_LINE ("\\n"),
        TK_PUBLIC ("public"),
        TK_PRIVATE ("private"),
        TK_COMPANION ("companion"),
        TK_THREAD ("thread"),
        TK_RETURN ("return"),
        TK_CONSTRUCTOR ("constructor"),
        TK_FUNCTION ("(function|fun)"),
        TK_CLASS ("class"),
        TK_OBJECT ("object"),
        TK_USE ("use "),
        TK_COMPANION_ACCESS ("::"),
        TK_COLON (":"),
        TK_MINUS ("-"),
        TK_PLUS ("\\+"),
        TK_MUL ("\\*"),
        TK_DIV ("/"),
        TK_AND ("&&"),
        TK_OR ("\\|\\|"),
        TK_LESS ("<"),
        TK_LEG ("<="),
        TK_GT (">"),
        TK_GEQ (">="),
        TK_EQ ("=="),
        TK_NOT_EQ ("!="),
        TK_ASSIGN ("="),
        TK_OPEN ("\\("),
        TK_CLOSE ("\\)"),
        TK_SEMI (";"),
        TK_COMMA (","),
        TK_ACCESS ("\\."),
        TK_VAR ("var"),
        TK_KEY_AS (" as "),
        TK_KEY_IS (" is "),
        TK_KEY_IF ("if"),
        TK_KEY_ELSE ("else"),
        TK_NOT ("!"),
        TK_OPEN_SQUARE_BRACKET("\\["),
        TK_CLOSE_SQUARE_BRACKET("\\]"),
        OPEN_BRACKET ("\\{"),
        CLOSE_BRACKET ("\\}"),
        DIFFERENT ("<>"),

        BOOL ("(true|false)"),
        STRING ("(\"[^\"]+\"|\"\")"),
        INTEGER ("\\d+"),
        REAL ("(\\d*)\\.\\d+"),
        IDENTIFIER ("\\w+");

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

