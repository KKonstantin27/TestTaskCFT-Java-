package servicesTests.statisticsServicesTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import services.statisticsServices.IntegersStatisticsService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegersStatisticsServiceTest {
    private static IntegersStatisticsService integersStatisticsService = new IntegersStatisticsService();
    private static IntegersStatisticsService integersFullStatisticsService = new IntegersStatisticsService();

    @BeforeAll
    public static void sendTestDataToCalculateStatistics() {
        String[] testData = {"0", "1234567890123456789", "-1234567890", "-12345", "1234567"};
        Arrays.stream(testData).forEach(x -> integersStatisticsService.calculateStatistics(x, false));
        Arrays.stream(testData).forEach(x -> integersFullStatisticsService.calculateStatistics(x, true));
    }

    @Test
    @Order(1)
    public void calculateStatisticsShouldCalculateOnlyNumberOfLinesField() {
        assertEquals(5, integersStatisticsService.getNumberOfLines());
        assertEquals(Long.MAX_VALUE, integersStatisticsService.getMin());
        assertEquals(Long.MIN_VALUE, integersStatisticsService.getMax());
        assertEquals(0.0, integersStatisticsService.getAvg());
        assertEquals(0, integersStatisticsService.getSum());
    }

    @Test
    @Order(2)
    public void calculateStatisticsShouldCalculateAllFields() {
        assertEquals(5, integersFullStatisticsService.getNumberOfLines());
        assertEquals(-1234567890, integersFullStatisticsService.getMin());
        assertEquals(1234567890123456789L, integersFullStatisticsService.getMax());
        assertEquals(2.4691357777802224E17, integersFullStatisticsService.getAvg());
        assertEquals(1234567888890111121L, integersFullStatisticsService.getSum());
    }

    @Test
    @Order(3)
    public void printStatisticsShouldPrintOnlyNumberOfLinesField() {
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));
        String expectedOutput = "Количество целых чисел: 5\r\n";
        integersStatisticsService.printStatistics(false);
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
        integersFullStatisticsService.printStatistics(true);
        assertEquals(expectedOutput, consoleOutput.toString().replaceAll("\u00a0", ""));
    }

    @AfterAll
    public static void clearTestEnvironment() {
        System.setOut(System.out);
    }
}
