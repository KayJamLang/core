package com.github.kayjamlang.core.parser;

import java.util.regex.Pattern;

public enum KayJamParserKeywords {
    VAR("var"),
    FUNCTION("fun"),
    NAMED("named"),
    PUBLIC("public"),
    PRIVATE("private"),
    INTERNAL("internal"),
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
    PROPERTY("prop"),
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
