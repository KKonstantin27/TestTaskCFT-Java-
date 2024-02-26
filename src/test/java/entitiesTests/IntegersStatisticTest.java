package entitiesTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import entities.IntegersStatistic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegersStatisticsTest {
    private static final IntegersStatistic INTEGERS_STATISTICS = new IntegersStatistic();
    private static final IntegersStatistic INTEGERS_FULL_STATISTICS = new IntegersStatistic();

    @BeforeAll
    public static void sendTestDataToCalculateStatistics() {
        String[] testData = {"0", "1234567890123456789", "-1234567890", "-12345", "1234567"};
        Arrays.stream(testData).forEach(x -> INTEGERS_STATISTICS.calculateStatistics(x, false));
        Arrays.stream(testData).forEach(x -> INTEGERS_FULL_STATISTICS.calculateStatistics(x, true));
    }

    @Test
    @Order(1)
    public void calculateStatisticsShouldCalculateOnlyNumberOfLinesField() {
        assertEquals(5, INTEGERS_STATISTICS.getNumberOfLines());
        assertEquals(Long.MAX_VALUE, INTEGERS_STATISTICS.getMin());
        assertEquals(Long.MIN_VALUE, INTEGERS_STATISTICS.getMax());
        assertEquals(0.0, INTEGERS_STATISTICS.getAvg());
        assertEquals(0, INTEGERS_STATISTICS.getSum());
    }

    @Test
    @Order(2)
    public void calculateStatisticsShouldCalculateAllFields() {
        assertEquals(5, INTEGERS_FULL_STATISTICS.getNumberOfLines());
        assertEquals(-1234567890, INTEGERS_FULL_STATISTICS.getMin());
        assertEquals(1234567890123456789L, INTEGERS_FULL_STATISTICS.getMax());
        assertEquals(2.4691357777802224E17, INTEGERS_FULL_STATISTICS.getAvg());
        assertEquals(1234567888890111121L, INTEGERS_FULL_STATISTICS.getSum());
    }

    @Test
    @Order(3)
    public void printStatisticsShouldPrintOnlyNumberOfLinesField() {
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));
        String expectedOutput = "Количество целых чисел: 5\r\n";
        INTEGERS_STATISTICS.printStatistics(false);
        assertEquals(expectedOutput, consoleOutput.toString());
    }

    @Test
    @Order(4)
    public void printStatisticsShouldPrintAllFields() {
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));
        String expectedOutput = """
                Количество целых чисел: 5\r
                Минимальное целое число: -1234567890\r
                Максимальное целое число: 1234567890123456789\r
                Среднее целых чисел: 246913577778022240\r
                Сумма целых чисел: 1234567888890111121\r
                --------------------------------------------------\r
                """;
        INTEGERS_FULL_STATISTICS.printStatistics(true);
        assertEquals(expectedOutput, consoleOutput.toString().replaceAll("\u00a0", ""));
    }

    @AfterAll
    public static void clearTestEnvironment() {
        System.setOut(System.out);
    }
}
