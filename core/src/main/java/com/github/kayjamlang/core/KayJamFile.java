package com.github.kayjamlang.core;

import com.github.kayjamlang.core.expressions.data.Constant;
import com.github.kayjamlang.core.statements.ClassStatement;
import com.github.kayjamlang.core.statements.FunctionStatement;
import com.github.kayjamlang.core.statements.Statement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class KayJamFile {
    public String path;
    public String namespace = "\\";
    public List<Constant> constants = new ArrayList<>();
    public List<Usage> usages = new ArrayList<>();
    public List<FunctionStatement> functions = new ArrayList<>();
    public List<Statement> children = new ArrayList<>();
    public List<ClassStatement> classes = new ArrayList<>();

    public KayJamFile(String path){
        this.path = path;
    }

    public String getFileName() {
        String filename = new File(path).getName();
        if(filename.lastIndexOf('.') == -1)
            return filename;

        return filename.substring(0, filename.lastIndexOf('.'));
    }

    public static class Usage {
        public List<String> required;
        public final String from;
        public final int line;

        public Usage(List<String> required, String from, int line){
            this.required = required;
            this.from = from;
            this.line = line;
        }
    }
}
