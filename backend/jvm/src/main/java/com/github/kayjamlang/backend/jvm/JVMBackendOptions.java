package com.github.kayjamlang.backend.jvm;

import com.github.kayjamlang.backend.IBackendOptions;
import picocli.CommandLine;

@CommandLine.Command(name = "jvm", mixinStandardHelpOptions = true)
public class JVMBackendOptions implements IBackendOptions {

}