package com.github.kayjamlang.backend;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;

public interface IBackendCompiler {
    void addOptions(Options options);
    IBackendOptions parseOptions(CommandLine data);
    void compile(IOptions options) throws Exception;
}
