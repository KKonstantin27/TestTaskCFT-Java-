package entitiesTests;

import entities.FloatsStatistic;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FloatsStatisticTest {
    private static final FloatsStatistic FLOATS_STATISTIC = new FloatsStatistic();
    private static final FloatsStatistic FLOATS_FULL_STATISTIC = new FloatsStatistic();

    @BeforeAll
    public static void sendTestDataToCalculateStatistics() {
        String[] testData = {"0.0", "1.528535047E-25", "-12.3456789", "-1234.56789", "-1.23456789"};
        Arrays.stream(testData).forEach(x -> FLOATS_STATISTIC.calculateStatistics(x, false));
        Arrays.stream(testData).forEach(x -> FLOATS_FULL_STATISTIC.calculateStatistics(x, true));
    }

    @Test
    @Order(1)
    public void calculateStatisticsShouldCalculateOnlyNumberOfLinesField() {
        assertEquals(5, FLOATS_STATISTIC.getNumberOfLines());
        assertEquals(Double.MAX_VALUE, FLOATS_STATISTIC.getMin());
        assertEquals(Double.NEGATIVE_INFINITY, FLOATS_STATISTIC.getMax());
        assertEquals(0.0, FLOATS_STATISTIC.getAvg());
        assertEquals(0.0, FLOATS_STATISTIC.getSum());
    }

    @Test
    @Order(2)
    public void calculateStatisticsShouldCalculateAllFields() {
        assertEquals(5, FLOATS_FULL_STATISTIC.getNumberOfLines());
        assertEquals(-1234.56789, FLOATS_FULL_STATISTIC.getMin());
        assertEquals(1.528535047E-25, FLOATS_FULL_STATISTIC.getMax());
        assertEquals(-249.62962735800002, FLOATS_FULL_STATISTIC.getAvg());
        assertEquals(-1248.14813679, FLOATS_FULL_STATISTIC.getSum());
    }

    @Test
    @Order(3)
    public void printStatisticsShouldPrintOnlyNumberOfLinesField() {
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));
        String expectedOutput = "Количество вещественных чисел: 5\r\n";
        FLOATS_STATISTIC.printStatistics(false);
        assertEquals(expectedOutput, consoleOutput.toString());
    }

    @Test
    @Order(4)
    public void printStatisticsShouldPrintAllFields() {
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));
        String expectedOutput = """
                Количество вещественных чисел: 5\r
                Минимальное вещественное число: -1234,56789\r
                Максимальное вещественное число: 0,000000000000000000000000152854\r
                Среднее вещественных чисел: -249,62962735800002\r
                Сумма вещественных чисел: -1248,14813679\r
                --------------------------------------------------\r
                """;
        FLOATS_FULL_STATISTIC.printStatistics(true);
        assertEquals(expectedOutput, consoleOutput.toString().replaceAll("\u00a0", ""));
    }

    @AfterAll
    public static void clearTestEnvironment() {
        System.setOut(System.out);
    }
}
