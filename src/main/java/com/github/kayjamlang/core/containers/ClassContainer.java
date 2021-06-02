package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Stmt;
import com.github.kayjamlang.core.exceptions.ParserException;
import com.github.kayjamlang.core.expressions.VariableExpression;
import com.github.kayjamlang.core.opcodes.AccessType;

import java.util.ArrayList;
import java.util.List;

/**
 * Class expression group
 */
public class ClassContainer extends Stmt {
    /**
     * Name of class
     */
    public final String name;

    /**
     * Name of class parent
     */
    public final String extendsClass;

    /**
     * Classes implements
     */
    public final List<String> implementsClass;

    /**
     * Static companion of class
     */
    public ObjectContainer companion;

    /**
     * Class constructors
     */
    public List<ConstructorContainer> constructors = new ArrayList<>();

    /**
     * Properties in class
     */
    public List<VariableExpression> variables = new ArrayList<>();

    /**
     * Type of access
     */
    public final AccessType accessType;

    /**
     * Functions
     */
    public List<FunctionContainer> functions = new ArrayList<>();

    /**
     * @param name Name of class
     * @param extendsClass Name of class parent
     * @param implementsClass Classes implements
     * @param children Code in class (Only functions, constructors, properties and companion)
     * @param accessType Access of class
     * @param line Line of start class
     * @throws ParserException throws on unexpected expression in code
     */
    public ClassContainer(String name, String extendsClass, List<String> implementsClass,
                          List<Stmt> children, AccessType accessType, int line) throws ParserException {
        super(line);
        this.name = name;
        this.extendsClass = extendsClass;
        this.implementsClass = implementsClass;
        this.accessType = accessType;

        for(Stmt stmt: children) {
            if (stmt instanceof ObjectContainer) {
                ObjectContainer objectContainer = (ObjectContainer) stmt;
                if(objectContainer.accessType != AccessType.COMPANION)
                    throw new ParserException(line,
                            "The class can only contain variables and functions");

                if (companion != null)
                    throw new ParserException(stmt.line, "companion already exists");

            }else if(stmt instanceof ConstructorContainer){
                constructors.add((ConstructorContainer) stmt);
            }else if(stmt instanceof VariableExpression)
                variables.add((VariableExpression) stmt);
            else if(stmt instanceof FunctionContainer){
                functions.add((FunctionContainer) stmt);
            }else throw new ParserException(line,
                        "The class can only contain variables and functions");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ClassContainer clone() throws CloneNotSupportedException {
        ClassContainer classContainer = (ClassContainer) super.clone();
        classContainer.functions = (List<FunctionContainer>) ((ArrayList<FunctionContainer>) functions).clone();
        classContainer.constructors = (List<ConstructorContainer>) ((ArrayList<ConstructorContainer>) constructors).clone();
        classContainer.variables = (List<VariableExpression>) ((ArrayList<VariableExpression>) variables).clone();
        return classContainer;
    }

    @Override
    public String toString() {
        return name+"@class";
    }
}
