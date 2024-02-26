package entitiesTests;

import entities.StringsStatistic;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringsStatisticTest {
    private static final StringsStatistic STRINGS_STATISTIC = new StringsStatistic();
    private static final StringsStatistic STRINGS_FULL_STATISTIC = new StringsStatistic();

    @BeforeAll
    public static void sendTestDataToCalculateStatistics() {
        String[] testData = {"./{}()><?!@#$", "12345>/12345.}123", "./ТестоваяСтрокаааааааааааа.123123123123123123123123()",
                "Тестовая строка", "Test String"};
        Arrays.stream(testData).forEach(x -> STRINGS_STATISTIC.calculateStatistics(x, false));
        Arrays.stream(testData).forEach(x -> STRINGS_FULL_STATISTIC.calculateStatistics(x, true));
    }

    @Test
    @Order(1)
    public void calculateStatisticsShouldCalculateOnlyNumberOfLinesField() {
        assertEquals(5, STRINGS_STATISTIC.getNumberOfLines());
        assertEquals(2147483647, STRINGS_STATISTIC.getShortestLineLength());
        assertEquals(0, STRINGS_STATISTIC.getShortestLineSize());
        assertEquals(-2147483648, STRINGS_STATISTIC.getLongestLineLength());
        assertEquals(0, STRINGS_STATISTIC.getLongestLineSize());
    }

    @Test
    @Order(2)
    public void calculateStatisticsShouldCalculateAllFields() {
        assertEquals(5, STRINGS_FULL_STATISTIC.getNumberOfLines());
        assertEquals(11, STRINGS_FULL_STATISTIC.getShortestLineLength());
        assertEquals(11, STRINGS_FULL_STATISTIC.getShortestLineSize());
        assertEquals(54, STRINGS_FULL_STATISTIC.getLongestLineLength());
        assertEquals(79, STRINGS_FULL_STATISTIC.getLongestLineSize());
    }

    @Test
    @Order(3)
    public void printStatisticsShouldPrintOnlyNumberOfLinesField() {
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));
        String expectedOutput = "Количество строк: 5\r\n";
        STRINGS_STATISTIC.printStatistics(false);
        assertEquals(expectedOutput, consoleOutput.toString());
    }

    @Test
    @Order(4)
    public void printStatisticsShouldPrintAllFields() {
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));
        String expectedOutput = """
                Количество строк: 5\r
                Размер самой короткой строки: 11 Б\r
                Размер самой длинной строки: 79 Б\r
                --------------------------------------------------\r
                """;
        STRINGS_FULL_STATISTIC.printStatistics(true);
        assertEquals(expectedOutput, consoleOutput.toString());
    }

    @AfterAll
    public static void clearTestEnvironment() {
        System.setOut(System.out);
    }
}
