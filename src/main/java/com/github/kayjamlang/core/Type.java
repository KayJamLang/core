package com.github.kayjamlang.core;


import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.expressions.FunctionRef;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public class Type {
    //Types
    public static final Type INTEGER = new Type("int", Long.class, false);
    public static final Type DOUBLE = new Type("double", Double.class, false);
    public static final Type STRING = new Type("string", String.class, false);
    public static final Type BOOLEAN = new Type("bool", Boolean.class, false);
    public static final Type OBJECT = new Type("obj", ObjectContainer.class, false);
    public static final Type ARRAY = new Type("array", List.class, false);
    public static final Type VOID = new Type("void", Void.class, true);
    public static final Type FUNCTION_REF = new Type("func", FunctionRef.class, true);
    public static final Type RANGE = new Type("range", Range.class, true);

    public static final Type ANY = new Type("any", Object.class, false);

    //Class
    public final String name;
    public final Class<?> typeClass;
    public final boolean onlyForFunction;
    public boolean nullable = false;

    public Type(String name, Class<?> typeClass, boolean onlyForFunction){
        this.name = name;
        this.typeClass = typeClass;
        this.onlyForFunction = onlyForFunction;
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

        return new Type(clazz.getName(), clazz, false);
    }

    public static Type getType(String name, boolean isFunction, boolean nullable){
        for (Field f : Type.class.getDeclaredFields())
            if (Modifier.isStatic(f.getModifiers())&&f.getType().equals(Type.class))
                try {
                    Type type = (Type) f.get(Type.class);
                    type.nullable = nullable;
                    if(type.name.equals(name))
                        if(!type.onlyForFunction || isFunction)
                            return type;
                } catch (IllegalAccessException ignored) {}

        Type type = new Type(name, Object.class, false);
        type.nullable = nullable;

        return type;
    }
}
