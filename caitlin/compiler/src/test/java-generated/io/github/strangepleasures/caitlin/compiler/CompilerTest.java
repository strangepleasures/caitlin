/* This file was generated by the Caitlin compiler. Do not edit manually. */
package io.github.strangepleasures.caitlin.compiler;

import static io.github.strangepleasures.caitlin.stdlib.StdLib.*;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.Problem;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import static io.github.strangepleasures.caitlin.compiler.Caitlin.transpileFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CompilerTest {

    @TestFactory
    Collection<DynamicTest> test() {
        var dir = new File("src/test/resources/test-data");
        return Arrays.stream(dir.listFiles()).filter(f -> f.getName().toLowerCase().endsWith(".cat")).map(source -> DynamicTest.dynamicTest(source.getName(), () -> {
            var sourcePath = source.getPath();
            var baseName = sourcePath.substring(0, sourcePath.length() - 3);
            var errorPath = Paths.get(baseName + "error");
            var targetPath = Paths.get(baseName + "java");
            if (errorPath.toFile().exists()) {
                try {
                    transpileFile(source);
                    fail("Expected an error");
                } catch (ParseProblemException e) {
                    var error = Files.readString(errorPath);
                    assertEquals(error, e.getProblems().stream().map(Problem::getMessage).collect(Collectors.joining("\n")), "Error messages differ");
                }
            } else if (targetPath.toFile().exists()) {
                var actual = transpileFile(source);
                var expected = Files.readString(targetPath);
                assertEquals(expected, actual, "The generated file differs from the expected");
            } else {
                try {
                    var actual = transpileFile(source);
                    Files.writeString(targetPath, actual);
                } catch (ParseProblemException e) {
                    Files.writeString(errorPath, e.getProblems().stream().map(Problem::getMessage).collect(Collectors.joining("\n")));
                }
            }
        })).collect(Collectors.toList());
    }
}
