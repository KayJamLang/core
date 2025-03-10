package com.github.kayjamlang.core;

import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KayJamFile {
    public String path;
    public String namespace = "\\";
    public Map<String, Expression> constants = new HashMap<>();
    public List<Usage> usages = new ArrayList<>();
    public List<Expression> children = new ArrayList<>();
    public Map<String, ClassContainer> classes = new HashMap<>();

    public KayJamFile(String path){
        this.path = path;
    }

    public String getFileName() {
        String filename = new File(path).getName();
        if(filename.lastIndexOf('.') == -1)
            return filename;

        return filename.substring(0, filename.lastIndexOf('.'));
    }

    public static class Usage extends Expression {
        public List<String> required;
        public final String from;

        public Usage(List<String> required, String from, int line){
            super(AccessType.NONE, line);
            this.required = required;
            this.from = from;
        }

        @Override
        public boolean isConstant() {
            return false;
        }
    }
}
