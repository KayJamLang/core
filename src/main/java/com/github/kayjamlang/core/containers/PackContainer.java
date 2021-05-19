package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.expressions.ConstantValueExpression;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.UseExpression;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackContainer extends Container {

    public final String packName;

    public final Map<String, Object> constants = new HashMap<>();
    public final Map<String, PackContainer> packs = new HashMap<>();
    public final Map<String, ClassContainer> classes = new HashMap<>();
    public final List<UseExpression> usages = new ArrayList<>();

    public PackContainer(String packName, Container container, boolean otherExpressionAllow) throws ParserException {
        super(new ArrayList<>(), AccessType.NONE, 0);
        this.packName = packName;

        functions.addAll(container.functions);

        int head = 0;
        for(Expression expression: container.children){
            if(expression instanceof UseExpression){
                if(head!=0)
                    throw new ParserException(expression.line, "All use expressions must be above the rest!");

                usages.add((UseExpression) expression);
            }else head++;

            if(expression instanceof ConstantValueExpression){
                ConstantValueExpression constant = (ConstantValueExpression) expression;
                if(head==0)
                    head++;
                else if(head!=1)
                    throw new ParserException(expression.line, "All constant expressions must be above the rest!");

                if(constants.containsKey(constant.name))
                    throw new ParserException(expression.line, "Constant \""+constant.name+"\" already defined");

                constants.put(constant.name, constant.value.value);
            }

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
