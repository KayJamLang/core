package com.github.kayjamlang.core;


import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.expressions.FunctionRefExpression;
import com.github.kayjamlang.core.expressions.data.Range;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;

public class Type implements Cloneable {
    //Types
    public static final Type INTEGER = new Type("int", Long.class, false);
    public static final Type DOUBLE = new Type("double", Double.class, false);
    public static final Type LONG = new Type("long", Long.class, false);
    public static final Type STRING = new Type("string", String.class, false);
    public static final Type BOOLEAN = new Type("bool", Boolean.class, false);
    public static final Type OBJECT = new Type("obj", ObjectContainer.class, false);
    public static final Type ARRAY = new Type("array", List.class, false);
    public static final Type VOID = new Type("void", Void.class, true);
    public static final Type FUNCTION_REF = new Type("func", FunctionRefExpression.class, true);
    public static final Type RANGE = new Type("range", Range.class, true);

    public static final Type ANY = new Type("any", Object.class, false);

    //Class
    public final String name;
    public final Class<?> typeClass;
    public final boolean primitive;
    public final boolean onlyForFunction;
    public boolean nullable = false;

    private Type(String name, Class<?> typeClass, boolean onlyForFunction){
        this.name = name;
        this.typeClass = typeClass;
        this.onlyForFunction = onlyForFunction;
        this.primitive = true;
    }

    private Type(String name, Class<?> typeClass, boolean onlyForFunction, boolean primitive){
        this.name = name;
        this.typeClass = typeClass;
        this.onlyForFunction = onlyForFunction;
        this.primitive = primitive;
    }

    public Type(String name, Class<?> typeClass){
        this.name = name;
        this.typeClass = typeClass;
        this.onlyForFunction = false;
        this.primitive = false;
    }

    @Override
    public Type clone() {
        try {
            return (Type) super.clone();
        } catch (CloneNotSupportedException e) {
            Type type = new Type(name, typeClass, onlyForFunction, primitive);
            type.nullable = nullable;

            return type;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type type = (Type) o;
        return onlyForFunction == type.onlyForFunction &&
                Objects.equals(name, type.name) &&
                Objects.equals(typeClass, type.typeClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, typeClass, onlyForFunction, nullable);
    }

    public static Type getType(String name){
        return getType(name, false, false);
    }

    public static Type getType(String name, boolean isFunction){
        return getType(name, isFunction, false);
    }

    public static Type getType(Class<?> clazz){
        for (Field f : Type.class.getDeclaredFields())
            if (Modifier.isStatic(f.getModifiers())&&f.getType().equals(Type.class))
                try {
                    Type type = (Type) f.get(Type.class);
                    if(type.typeClass.equals(clazz))
                        return type;
                } catch (IllegalAccessException ignored) {}

        return new Type(clazz.getName(), clazz);
    }

    public static Type getType(String name, boolean isFunction, boolean nullable){
        for (Field f : Type.class.getDeclaredFields())
            if (Modifier.isStatic(f.getModifiers())&&f.getType().equals(Type.class))
                try {
                    Type type = (Type) f.get(Type.class);
                    type.nullable = nullable;
                    if(type.name.equals(name))
                        if(!type.onlyForFunction || isFunction)
                            return type.clone();
                } catch (IllegalAccessException ignored) {}

        Type type = new Type(name, Object.class);
        type.nullable = nullable;

        return type;
    }

    public static Type of(String name, boolean nullable){
        Type type = new Type(name, Object.class);
        type.nullable = nullable;
        return type.clone();
    }

    public static Type of(String name){
        return new Type(name, Object.class);
    }
}
