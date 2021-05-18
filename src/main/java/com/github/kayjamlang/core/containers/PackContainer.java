package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.UseExpression;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackContainer extends Container {

    public final String packName;

    public final Map<String, PackContainer> packs = new HashMap<>();
    public final Map<String, ClassContainer> classes = new HashMap<>();
    public final List<UseExpression> usages = new ArrayList<>();

    public PackContainer(String packName, Container container, boolean otherExpressionAllow) throws ParserException {
        super(new ArrayList<>(), AccessType.NONE, 0);
        this.packName = packName;

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
            }else if(expression instanceof PackContainer){
                PackContainer pack = (PackContainer) expression;
                packs.put(pack.packName, pack);
            }else if(otherExpressionAllow)
                this.children.add(expression);
        }
    }
}
