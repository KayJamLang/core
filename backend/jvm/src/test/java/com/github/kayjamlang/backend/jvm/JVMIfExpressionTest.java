package com.github.kayjamlang.backend.jvm;

import com.github.kayjamlang.core.KayJamFile; // May not be directly used, but good for context
import org.junit.Test;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.Assert.assertTrue;

public class JVMIfExpressionTest {

    @Test
    public void testIfElseExpressionCompilation() throws Exception {
        Path inputDir = null;
        Path outputDir = null;
        try {
            // a. Create temporary input and output directories
            inputDir = Files.createTempDirectory("kayjam_input_");
            outputDir = Files.createTempDirectory("kayjam_output_");

            // b. Create a KayJam source file string
            String fileName = "MyIfTest";
            // Assuming KayJam syntax for a class with a constant initialized by an if-expression.
            // The constant 'MyVal' will be part of the static initializer (<clinit>) of MyIfTestKJ.class
            String kayJamSource = "class " + fileName + " { const MyVal = if (true) { 1 } else { 2 } }";
            File sourceFile = new File(inputDir.toFile(), fileName + ".kj");

            try (FileWriter writer = new FileWriter(sourceFile)) {
                writer.write(kayJamSource);
            }

            // c. Create JVMBackendOptions
            JVMBackendOptions options = new JVMBackendOptions();
            options.setInputDir(inputDir.toFile());
            options.setOutputDir(outputDir.toFile());
            // options.setNamespace(""); // Assuming default empty namespace for now

            // d. Compile
            JVMBackendCompiler compiler = JVMBackendCompiler.INSTANCE;
            compiler.compile(options);

            // e. Check if the class file was created
            // Based on JVMKayJamExpressionVisitor:
            // namespace is normalized. If empty, it's likely just the class name.
            // className is normalizeClassName(container.name) + "KJ" for files, or normalizeClassName(classContainer.name) for classes.
            // For a class named MyIfTest, this should become MyIfTest.class (if it's a class container direct output)
            // or MyIfTestKJ.class if it's treated like a file container.
            // The current KayJamFile logic wraps classes within a file, so the file name matters.
            // The file is MyIfTest.kj, so it might generate MyIfTestKJ.class in the root of outputDir.
            // However, the KayJamSource defines a class "MyIfTest".
            // The visitKayJamFile method in JVMKayJamExpressionVisitor processes classes defined within the file.
            // It uses `packageName + "/" + normalizeClassName(classContainer.name)`
            // Adjust class name based on actual naming convention by compiler
            // As per subtask description, checking for fileName + "KJ.class"
            File expectedClassFile = new File(outputDir.toFile(), fileName + "KJ.class"); 
            assertTrue("Expected class file was not generated: " + expectedClassFile.getAbsolutePath(), expectedClassFile.exists());

        } finally {
            // f. Cleanup (optional for worker, but good practice)
            if (inputDir != null) {
                // Simple cleanup, could be more robust
                Files.walk(inputDir)
                        .sorted(java.util.Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
            if (outputDir != null) {
                Files.walk(outputDir)
                        .sorted(java.util.Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        }
    }
}
