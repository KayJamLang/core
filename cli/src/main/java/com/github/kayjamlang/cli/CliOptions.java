package com.github.kayjamlang.cli;

import com.github.kayjamlang.backend.IBackendOptions;
import com.github.kayjamlang.backend.IOptions;

import java.io.File;

public class CliOptions implements IOptions {
    private final IBackendOptions backendOptions;
    private final File outputDir;
    private final File inputDir;

    public CliOptions(IBackendOptions backendOptions, File outputDir, File inputDir) {
        this.backendOptions = backendOptions;
        this.outputDir = outputDir;
        this.inputDir = inputDir;
    }

    @Override
    public IBackendOptions getBackendOptions() {
        return backendOptions;
    }

    @Override
    public File getOutputDir() {
        return outputDir;
    }

    @Override
    public File getInputDir() {
        return inputDir;
    }
}
