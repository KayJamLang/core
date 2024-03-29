package com.github.kayjamlang.core;

import java.util.HashSet;
import java.util.Set;

public class KayJamLexer {
    public KayJamFile source;
    public StringBuilder input;
    private Token token;
    private int line = 1;
    private boolean finished = false;
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
        if (finished) {
            return;
        }

        if (input.length() == 0) {
            finished = true;
            return;
        }

        fixNewLine();
        ignoreWhiteSpaces();

        if (findNextToken()) {
            while (token.type==Token.Type.TK_NEW_LINE||token.type==Token.Type.COMMENT){
                line++;

                if (input.length() == 0) {
                    finished = true;
                    return;
                }

                ignoreWhiteSpaces();
                findNextToken();
            }
            return;
        }

        finished = true;

        if (input.length() > 0) {
            errorMessage = "Unexpected symbol: '" + input.charAt(0) + "'";
        }
    }

    private void ignoreWhiteSpaces() {
        int charsToDelete = 0;

        while (blankChars.contains(input.charAt(charsToDelete))) {
            charsToDelete++;
            if(input.toString().length()==charsToDelete)
                break;
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

    public boolean isFinished() {
        return finished;
    }

    public String errorMessage() {
        return errorMessage;
    }
}