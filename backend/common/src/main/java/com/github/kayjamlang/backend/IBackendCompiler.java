package com.github.kayjamlang.backend;

public interface IBackendCompiler {
    IBackendOptions createOptionsClass();
    void compile(IOptions options) throws Exception;
}
