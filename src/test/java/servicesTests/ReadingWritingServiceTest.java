package servicesTests;

import enums.TypeOfLine;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import services.ReadingWritingService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReadingWritingServiceTest {
    private static ReadingWritingService readingWritingService;
    private static Path[] testWritingPaths;
    private static List<Path> testReadingPaths;

    @BeforeAll
    public static void prepareTestEnvironment() throws IOException {
        testReadingPaths = new ArrayList<>(List.of(
                Path.of("src/test/resources/testFiles/testInput1.txt"),
                Path.of("src/test/resources/testFiles/testInput2.txt"),
                Path.of("src/test/resources/testFiles/testInput3.txt")
        ));

        String[][] testInputStrings = {
                {"Lorem ipsum dolor sit amet\n", "45\n", "Пример\n", "3.1415\n", "consectetur adipiscing\n", "-0.001\n",
                        "тестовое задание\n", "100500\n"},
                {"Нормальная форма числа с плавающей запятой\n", "1.528535047E-25\n", "Long\n", "1234567890123456789\n"},
                {"1234567\n", "123.4567\n", "-1234\n", "-12.34\n"}
        };

        for (int i = 0; i < testReadingPaths.size(); i++) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testReadingPaths.get(i).toString(), true))) {
                for (int j = 0; j < testInputStrings[i].length; j++) {
                    bufferedWriter.write(testInputStrings[i][j]);
                }
            }
        }

        testWritingPaths = new Path[]{
                Path.of("src/test/resources/testFiles/floats.txt"),
                Path.of("src/test/resources/testFiles/integers.txt"),
                Path.of("src/test/resources/testFiles/strings.txt")
        };

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testWritingPaths[1].toString(), true))) {
            bufferedWriter.write("123456789\n");
            bufferedWriter.write("0\n");
        }
    }

    @Test
    @Order(1)
    public void testReadWriteToFilesWithRewriteBehaviorAndWithEmptyOutputFiles() throws IOException {
        readingWritingService = new ReadingWritingService();
        List<Path> readingPaths = testReadingPaths.subList(2, 3);
        readingWritingService.readWriteToFiles(readingPaths, testWritingPaths, false,
                false, false);

        String expectedFloatsOutput = """
                123.4567
                -12.34
                """;

        String expectedIntegersOutput = """
                123456789
                0
                1234567
                -1234
                """;

        String actualFloatsOutput = readFromFile(testWritingPaths[TypeOfLine.FLOAT.getPathIndex()]);
        String actualIntegersOutput = readFromFile(testWritingPaths[TypeOfLine.INTEGER.getPathIndex()]);

        assertTrue(Files.exists(testWritingPaths[TypeOfLine.FLOAT.getPathIndex()]));
        assertTrue(Files.exists(testWritingPaths[TypeOfLine.INTEGER.getPathIndex()]));
        assertTrue(Files.notExists(testWritingPaths[TypeOfLine.STRING.getPathIndex()]));

        assertEquals(expectedFloatsOutput, actualFloatsOutput);
        assertEquals(expectedIntegersOutput, actualIntegersOutput);
    }

    @Test
    @Order(2)
    public void testReadWriteToFilesWithAddingBehaviorAndWithoutEmptyOutputFiles() throws IOException {
        readingWritingService = new ReadingWritingService();
        List<Path> readingPaths = testReadingPaths.subList(0, 2);
        readingWritingService.readWriteToFiles(readingPaths, testWritingPaths, true,
                false, false);

        String expectedFloatsOutput = """
                3.1415
                -0.001
                1.528535047E-25
                """;

        String expectedIntegersOutput = """
                45
                100500
                1234567890123456789
                """;

        String expectedStringsOutput = """
                Lorem ipsum dolor sit amet
                Пример
                consectetur adipiscing
                тестовое задание
                Нормальная форма числа с плавающей запятой
                Long
                """;

        String actualFloatsOutput = readFromFile(testWritingPaths[TypeOfLine.FLOAT.getPathIndex()]);
        String actualIntegersOutput = readFromFile(testWritingPaths[TypeOfLine.INTEGER.getPathIndex()]);
        String actualStringsOutput = readFromFile(testWritingPaths[TypeOfLine.STRING.getPathIndex()]);

        assertTrue(Files.exists(testWritingPaths[TypeOfLine.FLOAT.getPathIndex()]));
        assertTrue(Files.exists(testWritingPaths[TypeOfLine.INTEGER.getPathIndex()]));
        assertTrue(Files.exists(testWritingPaths[TypeOfLine.STRING.getPathIndex()]));

        assertEquals(expectedFloatsOutput, actualFloatsOutput);
        assertEquals(expectedIntegersOutput, actualIntegersOutput);
        assertEquals(expectedStringsOutput, actualStringsOutput);
    }

    @AfterAll
    public static void clearTestEnvironment() throws IOException {
        deleteTestFiles(testReadingPaths.toArray(new Path[0]));
        deleteTestFiles(testWritingPaths);
    }

    private String readFromFile(Path testFilePath) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader bufferedReader = Files.newBufferedReader(testFilePath)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append("\n");
            }
        }
        return result.toString();
    }

    private static void deleteTestFiles(Path[] testFilePaths) throws IOException {
        for (Path testFilePath : testFilePaths) {
            Files.deleteIfExists(testFilePath);
        }
    }
}
