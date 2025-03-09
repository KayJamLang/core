package com.github.kayjamlang.backend.ts;

import com.github.kayjamlang.backend.IBackendOptions;
import picocli.CommandLine;

@CommandLine.Command(name = "ts", mixinStandardHelpOptions = true)
public class TSBackendOptions implements IBackendOptions {

}