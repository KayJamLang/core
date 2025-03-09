package com.github.kayjamlang.core;

import java.util.regex.Pattern;

public enum KayJamParserKeywords {
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
    NAMESPACE("namespace"),
    USE("use"),
    CAST("as"),
    IS("is"),
    IF("if"),
    IN("in"),
    ELSE("else"),
    PACK("pack"),
    CONSTANT("const"),
    FROM("from"),

    ;

    private final Pattern pattern;

    KayJamParserKeywords(String regex) {
        pattern = Pattern.compile("^" + regex + "$");
    }

    public boolean endOfMatch(String s) {
        return pattern.matcher(s).find();
    }

    public static KayJamParserKeywords find(String str) {
        for(KayJamParserKeywords t : KayJamParserKeywords.values()) {
            if(t.endOfMatch(str))
                return t;
        }

        return null;
    }
}
