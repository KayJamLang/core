package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.UseExpression;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Script extends Container {

    public final String namespace;
    public final Map<String, ClassContainer> classes = new HashMap<>();
    public final List<UseExpression> usages = new ArrayList<>();

    public Script(String namespace, Container container) throws ParserException {
        super(new ArrayList<>(), AccessType.NONE, 0);
        this.namespace = namespace;
        functions.addAll(container.functions);

        boolean usagesHeadFinished = false;
        for(Expression expression: container.children){
            if(expression instanceof UseExpression){
                if(usagesHeadFinished)
                    throw new ParserException(expression.line, "All use expressions must be above the rest!");

                usages.add((UseExpression) expression);
            }else usagesHeadFinished = true;

            if(expression instanceof ClassContainer){
                ClassContainer clazz = (ClassContainer) expression;
                classes.put(clazz.name, clazz);
            }
        }
    }
}
