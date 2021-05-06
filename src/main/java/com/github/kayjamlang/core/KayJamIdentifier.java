package com.github.kayjamlang.core;

import java.util.regex.Pattern;

public enum KayJamIdentifier {
    VAR("var"),
    FUNCTION("function|fun"),
    NAMED("named"),
    PUBLIC("public"),
    PRIVATE("private"),
    WHILE("while"),
    FOR("for"),
    CLASS("class"),
    OBJECT("object"),
    RETURN("return"),
    CONSTRUCTOR("constructor"),
    COMPANION("companion"),
    USE("use"),
    CAST("as"),
    IS("is"),
    IF("if"),
    IN("in"),
    ELSE("else"),

    ;

    private final Pattern pattern;

    KayJamIdentifier(String regex) {
        pattern = Pattern.compile("^" + regex + "$");
    }

    public boolean endOfMatch(String s) {
        return pattern.matcher(s).find();
    }

    public static KayJamIdentifier find(String str) {
        for(KayJamIdentifier t : KayJamIdentifier.values()) {
            if(t.endOfMatch(str))
                return t;
        }

        return null;
    }
}
