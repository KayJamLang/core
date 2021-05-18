package com.github.kayjamlang.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class Type implements Cloneable {
    public static final Type INTEGER = new Type("int",false);
    public static final Type DOUBLE = new Type("double",false);
    public static final Type LONG = new Type("long",false);
    public static final Type STRING = new Type("string",false);
    public static final Type BOOLEAN = new Type("bool",false);
    public static final Type OBJECT = new Type("obj",false);
    public static final Type ARRAY = new Type("array",false);
    public static final Type VOID = new Type("void",true);
    public static final Type FUNCTION_REF = new Type("func",true);
    public static final Type RANGE = new Type("range", true);

    public static final Type ANY = new Type("any", false);
    public static final Type NULL = new Type("null", true);

    //Class
    public final String name;

    @Deprecated
    public final Class<?> typeClass = null;

    public final boolean primitive;
    public final boolean onlyForFunction;
    public boolean nullable = false;

    private Type(String name){
        this.name = name;
        this.onlyForFunction = false;
        this.primitive = false;
    }

    private Type(String name, boolean onlyForFunction){
        this.name = name;
        this.onlyForFunction = onlyForFunction;
        this.primitive = true;
    }

    private Type(String name, boolean onlyForFunction, boolean primitive){
        this.name = name;
        this.onlyForFunction = onlyForFunction;
        this.primitive = primitive;
    }

    @Override
    public Type clone() {
        try {
            return (Type) super.clone();
        } catch (CloneNotSupportedException e) {
            Type type = new Type(name, onlyForFunction, primitive);
            type.nullable = nullable;

            return type;
        }
    }

    /**
     * Checks if a type supports another type
     * @param type Type for verify
     * @return Returns whether a type supports another type
     */
    public boolean isAccept(Type type) {
        if(type.equals(NULL) && (type.nullable || nullable))
            return true;

        return this.equals(type);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type type = (Type) o;

        return onlyForFunction == type.onlyForFunction &&
                Objects.equals(name, type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, onlyForFunction, nullable);
    }

    /**
     * Type creator
     * @param name Type name
     * @return Type
     */
    public static Type getType(String name){
        return getType(name, false, false);
    }

    /**
     * Type creator
     * @param name Type name
     * @param isFunction Can return function type
     * @return Type
     */
    public static Type getType(String name, boolean isFunction){
        return getType(name, isFunction, false);
    }

    @Deprecated
    public static Type getType(Class<?> clazz){
        return Type.ANY;
    }

    /**
     * Type creator
     * @param name Type name
     * @param isFunction Can return function type
     * @param nullable Is nullable type
     * @return Type
     */
    public static Type getType(String name, boolean isFunction, boolean nullable){
        for (Field f : Type.class.getDeclaredFields())
            if (Modifier.isStatic(f.getModifiers())&&f.getType().equals(Type.class))
                try {
                    Type type = (Type) f.get(Type.class);
                    type.nullable = nullable;
                    if(type.name.equals(name)||("\\"+type.name).equals(name))
                        if(!type.onlyForFunction || isFunction)
                            return type.clone();
                } catch (IllegalAccessException ignored) {}

        Type type = new Type(name);
        type.nullable = nullable;

        return type;
    }

    /**
     * Simple type creator
     * @param name Name of type
     * @param nullable Is nullable
     * @return Returns need type
     */
    public static Type of(String name, boolean nullable){
        Type type = new Type(name);
        type.nullable = nullable;
        return type.clone();
    }

    /**
     * Simple type creator (not nullable)
     * @param name Name of type
     * @return Returns need type
     */
    public static Type of(String name){
        return new Type(name);
    }

    @Deprecated
    private Type(String name, Class<?> typeClass, boolean onlyForFunction){
        this.name = name;
        this.onlyForFunction = onlyForFunction;
        this.primitive = true;
    }

    @Deprecated
    private Type(String name, Class<?> typeClass, boolean onlyForFunction, boolean primitive){
        this.name = name;
        this.onlyForFunction = onlyForFunction;
        this.primitive = primitive;
    }

    @Deprecated
    public Type(String name, Class<?> typeClass){
        this.name = name;
        this.onlyForFunction = false;
        this.primitive = false;
    }
}
