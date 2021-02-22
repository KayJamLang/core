package com.github.kayjamlang.core.containers;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;

import java.util.List;

public class ThreadContainer extends Container {

    public ThreadContainer(List<Expression> children, int line) {
        super(children, AccessIdentifier.NONE, line);
        globalVariables = false;
    }

    @Override
    public Object onExecute(final Container parent) {
        mainContainer = parent.mainContainer;

        new Thread(() -> {
            try {
                ThreadContainer.super.execute(parent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        return null;
    }
}