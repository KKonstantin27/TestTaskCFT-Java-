package servicesTests.statisticsServicesTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import services.statisticsServices.StringsStatisticsService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringsStatisticsServiceTest {
    private static StringsStatisticsService stringsStatisticsService = new StringsStatisticsService();
    private static StringsStatisticsService stringsFullStatisticsService = new StringsStatisticsService();

    @BeforeAll
    public static void sendTestDataToCalculateStatistics() {
        String[] testData = {"./{}()><?!@#$", "12345>/12345.}123", "./ТестоваяСтрокаааааааааааа.123123123123123123123123()",
                "Тестовая строка", "Test String"};
        Arrays.stream(testData).forEach(x -> stringsStatisticsService.calculateStatistics(x, false));
        Arrays.stream(testData).forEach(x -> stringsFullStatisticsService.calculateStatistics(x, true));
    }

    @Test
    @Order(1)
    public void calculateStatisticsShouldCalculateOnlyNumberOfLinesField() {
        assertEquals(5, stringsStatisticsService.getNumberOfLines());
        assertEquals(2147483647, stringsStatisticsService.getShortestLineLength());
        assertEquals(0, stringsStatisticsService.getShortestLineSize());
        assertEquals(-2147483648, stringsStatisticsService.getLongestLineLength());
        assertEquals(0, stringsStatisticsService.getLongestLineSize());
    }

    @Test
    @Order(2)
    public void calculateStatisticsShouldCalculateAllFields() {
        assertEquals(5, stringsFullStatisticsService.getNumberOfLines());
        assertEquals(11, stringsFullStatisticsService.getShortestLineLength());
        assertEquals(11, stringsFullStatisticsService.getShortestLineSize());
        assertEquals(54, stringsFullStatisticsService.getLongestLineLength());
        assertEquals(79, stringsFullStatisticsService.getLongestLineSize());
    }

    @Test
    @Order(3)
    public void printStatisticsShouldPrintOnlyNumberOfLinesField() {
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));
        String expectedOutput = "Количество строк: 5\r\n";
        stringsStatisticsService.printStatistics(false);
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
        stringsFullStatisticsService.printStatistics(true);
        assertEquals(expectedOutput, consoleOutput.toString());
    }

    @AfterAll
    public static void clearTestEnvironment() {
        System.setOut(System.out);
    }
}
