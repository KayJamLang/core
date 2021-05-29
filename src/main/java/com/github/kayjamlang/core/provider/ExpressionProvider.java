package com.github.kayjamlang.core.provider;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.TypeException;

public abstract class ExpressionProvider<ET, RO, CO, MCO> {
    /**
     * @deprecated please replace to {@code mainProvider.defaultObject}
     */
    @Deprecated
    public RO provide(MainExpressionProvider<RO, CO, MCO> mainProvider, CO context, CO argsContext, ET expression) {
        return mainProvider.defaultObject;
    }

    /**
     * @deprecated please don't use this
     */
    @Deprecated
    public Type getType(MainExpressionProvider<RO, CO, MCO> mainProvider, CO context, CO argsContext, ET expression) throws TypeException {
        return Type.ANY;
    }
}
