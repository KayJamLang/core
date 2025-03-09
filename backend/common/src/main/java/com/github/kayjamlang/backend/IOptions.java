package com.github.kayjamlang.backend;

import java.io.File;

public interface IOptions {
    IBackendOptions getBackendOptions();
    File getOutputDir();
    File getInputDir();
}
