package com.github.kayjamlang.cli;

import com.github.kayjamlang.backend.IBackendCompiler;
import com.github.kayjamlang.backend.IBackendOptions;
import com.github.kayjamlang.backend.jvm.JVMBackendCompiler;
import com.github.kayjamlang.backend.ts.TSBackendCompiler;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@Command(name = "kayjam", mixinStandardHelpOptions = true)
public class MainCli implements Callable<Integer> {
    private final Map<String, IBackendCompiler> compilers = new HashMap<>();
    private final String compiler;
    private final String[] args;

    @Option(names = {"-o", "--output"}, description = "Folder for output compiled files", required = true)
    private String output;

    @Option(names = {"-i", "--input"}, description = "Folder for input kayjam files", required = true)
    private String input;


    public MainCli(String compiler, String[] args) {
        this.compiler = compiler;
        this.args = args;
        compilers.put("jvm", JVMBackendCompiler.INSTANCE);
        compilers.put("ts", TSBackendCompiler.INSTANCE);
    }

    @Override
    public Integer call() throws Exception {
        if (compilers.containsKey(compiler)) {
            IBackendCompiler backendCompiler = compilers.get(compiler);
            IBackendOptions backendOptions = backendCompiler.createOptionsClass();

            CommandLine line = new CommandLine(backendOptions);
            line.setUnmatchedArgumentsAllowed(true);
            line.parseArgs(args);

            CliOptions cliOptions = new CliOptions(
                    backendOptions,
                    new File(output),
                    new File(input)
            );

            backendCompiler.compile(cliOptions);
        } else {
            System.err.println("Unknown backend type: " + compiler);
            return 1;
        }

        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new MainCli(args[0], Arrays.copyOfRange(args, 1, args.length)))
                .execute(Arrays.copyOfRange(args, 1, args.length));
        System.exit(exitCode);
    }
}
