package com.github.kayjam.core;

import com.github.kayjam.core.containers.ObjectContainer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Type {
    //Types
    public static final Type ANY = new Type("any", Object.class, false);
    public static final Type INTEGER = new Type("int", Integer.class, false);
    public static final Type BOOLEAN = new Type("bool", Boolean.class, false);
    public static final Type OBJECT = new Type("obj", Object.class, false);
    public static final Type VOID = new Type("void", Void.class, true);

    //Class
    public final String name;
    public final Class<?> typeClass;
    public final boolean onlyForFunction;

    public Type(String name, Class<?> typeClass, boolean onlyForFunction){
        this.name = name;
        this.typeClass = typeClass;
        this.onlyForFunction = onlyForFunction;
    }

    public static Type getType(String name){
        return getType(name, false);
    }

    public static Type getType(Class<?> clazz){
        for (Field f : Type.class.getDeclaredFields())
            if (Modifier.isStatic(f.getModifiers())&&f.getType().equals(Type.class))
                try {
                    Type type = (Type) f.get(Type.class);
                    if(type.typeClass.equals(clazz))
                        return type;
                } catch (IllegalAccessException ignored) {}

        return ANY;
    }

    public static Type getType(String name, boolean isFunction){
        for (Field f : Type.class.getDeclaredFields())
            if (Modifier.isStatic(f.getModifiers())&&f.getType().equals(Type.class))
                try {
                    Type type = (Type) f.get(Type.class);
                    if(type.name.equals(name)&&type.onlyForFunction==isFunction)
                        return type;
                } catch (IllegalAccessException ignored) {}

        return new Type(name, Object.class, false);
    }
}
