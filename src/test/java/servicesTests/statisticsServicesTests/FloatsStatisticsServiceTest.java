package servicesTests.statisticsServicesTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import services.statisticsServices.FloatsStatisticsService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FloatsStatisticsServiceTest {
    private static FloatsStatisticsService floatsStatisticsService = new FloatsStatisticsService();
    private static FloatsStatisticsService floatsFullStatisticsService = new FloatsStatisticsService();

    @BeforeAll
    public static void sendTestDataToCalculateStatistics() {
        String[] testData = {"0.0", "1.528535047E-25", "-12.3456789", "-1234.56789", "-1.23456789"};
        Arrays.stream(testData).forEach(x -> floatsStatisticsService.calculateStatistics(x, false));
        Arrays.stream(testData).forEach(x -> floatsFullStatisticsService.calculateStatistics(x, true));
    }

    @Test
    @Order(1)
    public void calculateStatisticsShouldCalculateOnlyNumberOfLinesField() {
        assertEquals(5, floatsStatisticsService.getNumberOfLines());
        assertEquals(Double.MAX_VALUE, floatsStatisticsService.getMin());
        assertEquals(Double.MIN_VALUE, floatsStatisticsService.getMax());
        assertEquals(0.0, floatsStatisticsService.getAvg());
        assertEquals(0.0, floatsStatisticsService.getSum());
    }

    @Test
    @Order(2)
    public void calculateStatisticsShouldCalculateAllFields() {
        assertEquals(5, floatsFullStatisticsService.getNumberOfLines());
        assertEquals(-1234.56789, floatsFullStatisticsService.getMin());
        assertEquals(1.528535047E-25, floatsFullStatisticsService.getMax());
        assertEquals(-249.62962735800002, floatsFullStatisticsService.getAvg());
        assertEquals(-1248.14813679, floatsFullStatisticsService.getSum());
    }

    @Test
    @Order(3)
    public void printStatisticsShouldPrintOnlyNumberOfLinesField() {
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));
        String expectedOutput = "Количество вещественных чисел: 5\r\n";
        floatsStatisticsService.printStatistics(false);
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
        floatsFullStatisticsService.printStatistics(true);
        assertEquals(expectedOutput, consoleOutput.toString().replaceAll("\u00a0", ""));
    }

    @AfterAll
    public static void clearTestEnvironment() {
        System.setOut(System.out);
    }
}
