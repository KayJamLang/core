package com.github.kayjamlang.cli;

import com.github.kayjamlang.backend.IBackendCompiler;
import com.github.kayjamlang.backend.IBackendOptions;
import com.github.kayjamlang.backend.jvm.JVMBackendCompiler;
import com.github.kayjamlang.backend.ts.TSBackendCompiler;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainCli {
    private static final Map<String, IBackendCompiler> compilers = new HashMap<>();

    static {
        compilers.put("jvm", JVMBackendCompiler.INSTANCE);
        compilers.put("ts", TSBackendCompiler.INSTANCE);
    }

    public static void main(String[] args) {
        Option typeOption = new Option("t", "type", true, "Backend type: " + compilers.keySet());
        typeOption.setRequired(true);

        Option outputOption = new Option("o", "output", true, "Output folder");
        outputOption.setRequired(true);

        Option inputOption = new Option("i", "input", true, "Input folder");
        inputOption.setRequired(true);

        Options options = new Options();
        options.addOption(typeOption);
        options.addOption(outputOption);
        options.addOption(inputOption);

        CommandLineParser cmdLinePosixParser = new DefaultParser();
        try {
            CommandLine commandLine = cmdLinePosixParser.parse(options, args);
            String type = commandLine.getOptionValue(typeOption).replace(" ", "");
            System.out.println(compilers.get(type));
            System.out.println("\'"+type+"\'");
            if (compilers.containsKey(type)) {
                IBackendCompiler backendCompiler = compilers.get(type);
                backendCompiler.addOptions(options);

                commandLine = cmdLinePosixParser.parse(options, args);
                IBackendOptions backendOptions = backendCompiler.parseOptions(commandLine);
                CliOptions cliOptions = new CliOptions(
                        backendOptions,
                        new File(commandLine.getOptionValue(outputOption)),
                        new File(commandLine.getOptionValue(inputOption))
                );

                backendCompiler.compile(cliOptions);
            } else {
                System.err.println("Unknown backend type: " + type);
                printHelp(options);
                System.exit(1);
            }
        } catch (ParseException e) {
            printHelp(options);
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void printHelp(final Options options) {
        final String commandLineSyntax = "kayjam -t jvm -i input/ -o output/";
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(commandLineSyntax, options);
    }
}
