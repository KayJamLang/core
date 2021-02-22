package com.github.kayjamlang.core.provider;

import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.Container;

import java.util.HashMap;
import java.util.Map;

public class MainContext extends Context {
    public Map<String, ClassContainer> classes = new HashMap<>();

    public MainContext(Container parent, Context parentContext) {
        super(parent, parentContext);
    }
}
