package com.github.kayjam.core.containers;

import com.github.kayjam.core.libs.LangLibrary;
import com.github.kayjam.core.opcodes.AccessIdentifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainContainer extends Container {
    public final Map<String, Object> data = new HashMap<>();

    public MainContainer() {
        super(new ArrayList<>(), AccessIdentifier.PRIVATE, 0);
        mainContainer = this;

        addLibrary(LangLibrary.class);
    }

    @Deprecated
    public void addLibrary(final Class<?> libraryClass){
        for(final Method method: libraryClass.getDeclaredMethods()){
            if(method.getAnnotation(KayJamFunction.class)!=null&&
                    (method.getModifiers()& Modifier.STATIC)!=0){

                List<String> args = new ArrayList<>();
                int count = 0;
                for (int i = 0; i < method.getGenericParameterTypes().length-1; i++) {
                    args.add("arg"+i);
                    count++;
                }

                final int finalCount = count;
                /*functions.add(new Function(method.getName(), new ArrayList<>(), AccessIdentifier.NONE,
                        args, 0, null, new ArrayList<>()){
                    @Override
                    public Object onExecute(Container parent) throws Exception {
                        variables.remove("this");
                        variables.remove("java");

                        Object[] parameters = new Object[variables.size()+1];
                        parameters[0] = MainContainer.this;

                        int i = 1;
                        for(Map.Entry<String, Object> entry: variables.entrySet()) {
                            if(entry.getKey().startsWith("arg")&&i-1<finalCount) {
                                parameters[i] = entry.getValue();
                                i++;
                            }else if(i-1==finalCount)
                                break;
                        }

                        Method method1 =
                                method.getClass()
                                        .getDeclaredMethod("invoke", Object.class, Object[].class);
                        return method1.invoke(method, libraryClass.newInstance(), parameters);
                    }
                });*/
            }
        }
    }

    @Override
    public Object onExecute(Container parent) {
        return null;
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface KayJamFunction {}
}
