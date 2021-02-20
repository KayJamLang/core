package com.github.kayjam.core.provider;

import com.github.kayjam.core.containers.ClassContainer;
import com.github.kayjam.core.containers.Container;

import java.util.HashMap;
import java.util.Map;

public class MainContext extends Context {
    public Map<String, ClassContainer> classes = new HashMap<>();

    public MainContext(Container parent, Context parentContext) {
        super(parent, parentContext);
    }
}
