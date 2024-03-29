package entitiesTests;

import entities.IntegersStatistic;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegersStatisticTest {
    private static final IntegersStatistic INTEGERS_STATISTIC = new IntegersStatistic();
    private static final IntegersStatistic INTEGERS_FULL_STATISTIC = new IntegersStatistic();

    @BeforeAll
    public static void sendTestDataToCalculateStatistics() {
        String[] testData = {"0", "1234567890123456789", "-1234567890", "-12345", "1234567"};
        Arrays.stream(testData).forEach(x -> INTEGERS_STATISTIC.calculateStatistics(x, false));
        Arrays.stream(testData).forEach(x -> INTEGERS_FULL_STATISTIC.calculateStatistics(x, true));
    }

    @Test
    @Order(1)
    public void calculateStatisticsShouldCalculateOnlyNumberOfLinesField() {
        assertEquals(5, INTEGERS_STATISTIC.getNumberOfLines());
        assertEquals(Long.MAX_VALUE, INTEGERS_STATISTIC.getMin());
        assertEquals(Long.MIN_VALUE, INTEGERS_STATISTIC.getMax());
        assertEquals(0.0, INTEGERS_STATISTIC.getAvg());
        assertEquals(0, INTEGERS_STATISTIC.getSum());
    }

    @Test
    @Order(2)
    public void calculateStatisticsShouldCalculateAllFields() {
        assertEquals(5, INTEGERS_FULL_STATISTIC.getNumberOfLines());
        assertEquals(-1234567890, INTEGERS_FULL_STATISTIC.getMin());
        assertEquals(1234567890123456789L, INTEGERS_FULL_STATISTIC.getMax());
        assertEquals(2.4691357777802224E17, INTEGERS_FULL_STATISTIC.getAvg());
        assertEquals(1234567888890111121L, INTEGERS_FULL_STATISTIC.getSum());
    }

    @Test
    @Order(3)
    public void printStatisticsShouldPrintOnlyNumberOfLinesField() {
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));
        String expectedOutput = "Количество целых чисел: 5\r\n";
        INTEGERS_STATISTIC.printStatistics(false);
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
        INTEGERS_FULL_STATISTIC.printStatistics(true);
        assertEquals(expectedOutput, consoleOutput.toString().replaceAll("\u00a0", ""));
    }

    @AfterAll
    public static void clearTestEnvironment() {
        System.setOut(System.out);
    }
}
