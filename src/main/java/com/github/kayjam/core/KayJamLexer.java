package com.github.kayjam.core;

import java.util.HashSet;
import java.util.Set;

public class KayJamLexer {
    public StringBuilder input;
    private Token token;
    private int line = 1;
    private int position = 0;
    private boolean exausthed = false;
    private String errorMessage = "";
    private final Set<Character> blankChars = new HashSet<>();

    public KayJamLexer(String value) {
        input = new StringBuilder(value);

        blankChars.add((char) 8);
        blankChars.add((char) 9);
        blankChars.add((char) 11);
        blankChars.add((char) 12);
        blankChars.add((char) 32);

        moveAhead();
    }

    public void moveAhead() {
        if (exausthed) {
            return;
        }

        if (input.length() == 0) {
            exausthed = true;
            return;
        }

        fixNewLine();
        ignoreWhiteSpaces();

        if (findNextToken()) {
            while (token.type==Token.Type.TK_NEW_LINE||token.type==Token.Type.COMMENT){
                line++;

                ignoreWhiteSpaces();
                findNextToken();
            }
            return;
        }

        exausthed = true;

        if (input.length() > 0) {
            errorMessage = "Unexpected symbol: '" + input.charAt(0) + "'";
        }
    }

    private void ignoreWhiteSpaces() {
        int charsToDelete = 0;

        while (blankChars.contains(input.charAt(charsToDelete))) {
            charsToDelete++;
        }

        if (charsToDelete > 0) {
            input.delete(0, charsToDelete);
        }
    }

    private void fixNewLine() {
        input = new StringBuilder(input.toString().replace('\r', ' '));
    }

    public int getLine(){
        return line;
    }

    public int getPosition() {
        return position;
    }

    private boolean findNextToken() {
        for (Token.Type t : Token.Type.values()) {
            int end = t.endOfMatch(input.toString());

            if (end != -1) {
                String lexema = input.substring(0, end);
                token = new Token(t, lexema);
                input.delete(0, end);

                return true;
            }
        }

        return false;
    }

    public Token currentToken() {
        return token;
    }

    public boolean isSuccessful() {
        return errorMessage.isEmpty();
    }

    public String errorMessage() {
        return errorMessage;
    }

    public boolean isExausthed() {
        return exausthed;
    }
}