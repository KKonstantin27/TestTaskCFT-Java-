package servicesTests;

import entities.FloatsStatistic;
import entities.IntegersStatistic;
import entities.StringsStatistic;
import enums.TypeOfLine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import services.StatisticsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StatisticsServiceTest {
    private static final StatisticsService STATISTICS_SERVICE = new StatisticsService();
    private static final FloatsStatistic FLOATS_STATISTIC_MOCK = Mockito.mock(FloatsStatistic.class);
    private static final IntegersStatistic INTEGERS_STATISTIC_MOCK = Mockito.mock(IntegersStatistic.class);
    private static final StringsStatistic STRINGS_STATISTIC_MOCK = Mockito.mock(StringsStatistic.class);

    @BeforeAll
    public static void prepareTestEnvironment() {
        STATISTICS_SERVICE.setFloatsStatistic(FLOATS_STATISTIC_MOCK);
        STATISTICS_SERVICE.setIntegersStatistic(INTEGERS_STATISTIC_MOCK);
        STATISTICS_SERVICE.setStringsStatistic(STRINGS_STATISTIC_MOCK);
    }

    @BeforeEach
    public void clearInvocationsInMockObjects() {
        clearInvocations(FLOATS_STATISTIC_MOCK, INTEGERS_STATISTIC_MOCK, STRINGS_STATISTIC_MOCK);
    }

    @Test
    @Order(1)
    public void calculateStatisticsShouldInvokeCalculatingMethods() {
        String testFloatLine = "0.12345";
        String testIntegerLine = "12345";
        String testStringLine = "Test string";

        STATISTICS_SERVICE.calculateStatistics(testFloatLine, true, TypeOfLine.FLOAT);
        verify(FLOATS_STATISTIC_MOCK).calculateStatistics(testFloatLine, true);
        STATISTICS_SERVICE.calculateStatistics(testIntegerLine, true, TypeOfLine.INTEGER);
        verify(INTEGERS_STATISTIC_MOCK).calculateStatistics(testIntegerLine, true);
        STATISTICS_SERVICE.calculateStatistics(testStringLine, true, TypeOfLine.STRING);
        verify(STRINGS_STATISTIC_MOCK).calculateStatistics(testStringLine, true);
    }

    @Test
    @Order(2)
    public void printStatisticsShouldNotInvokePrintingMethodsIfNumberOfLines0() {
        return0WhenInvokeGetNumberOfLinesForMockedObjects();

        STATISTICS_SERVICE.printStatistics(true, true);

        verify(FLOATS_STATISTIC_MOCK, never()).printStatistics(Mockito.anyBoolean());
        verify(INTEGERS_STATISTIC_MOCK, never()).printStatistics(Mockito.anyBoolean());
        verify(STRINGS_STATISTIC_MOCK, never()).printStatistics(Mockito.anyBoolean());
    }

    @Test
    @Order(3)
    public void printStatisticsShouldNotInvokePrintingMethodsIfIsThereStatisticsFalse() {
        return123WhenInvokeGetNumberOfLinesForMockedObjects();

        STATISTICS_SERVICE.printStatistics(false, false);

        verify(FLOATS_STATISTIC_MOCK, never()).printStatistics(Mockito.anyBoolean());
        verify(INTEGERS_STATISTIC_MOCK, never()).printStatistics(Mockito.anyBoolean());
        verify(STRINGS_STATISTIC_MOCK, never()).printStatistics(Mockito.anyBoolean());
    }

    @Test
    @Order(4)
    public void printStatisticsShouldInvokePrintingMethodsIfIsThereStatisticsTrue() {
        return123WhenInvokeGetNumberOfLinesForMockedObjects();

        STATISTICS_SERVICE.printStatistics(true, true);

        verify(FLOATS_STATISTIC_MOCK).printStatistics(Mockito.anyBoolean());
        verify(INTEGERS_STATISTIC_MOCK).printStatistics(Mockito.anyBoolean());
        verify(STRINGS_STATISTIC_MOCK).printStatistics(Mockito.anyBoolean());
    }

    @Test
    @Order(5)
    public void getNumberOfLinesOfThisTypeShouldReturnNumberFromStatistics() {
        return123WhenInvokeGetNumberOfLinesForMockedObjects();

        assertEquals(1, STATISTICS_SERVICE.getNumberOfLinesOfThisType(TypeOfLine.FLOAT));
        assertEquals(2, STATISTICS_SERVICE.getNumberOfLinesOfThisType(TypeOfLine.INTEGER));
        assertEquals(3, STATISTICS_SERVICE.getNumberOfLinesOfThisType(TypeOfLine.STRING));
    }

    private void return123WhenInvokeGetNumberOfLinesForMockedObjects() {
        when(FLOATS_STATISTIC_MOCK.getNumberOfLines()).thenReturn(1);
        when(INTEGERS_STATISTIC_MOCK.getNumberOfLines()).thenReturn(2);
        when(STRINGS_STATISTIC_MOCK.getNumberOfLines()).thenReturn(3);
    }

    private void return0WhenInvokeGetNumberOfLinesForMockedObjects() {
        when(FLOATS_STATISTIC_MOCK.getNumberOfLines()).thenReturn(0);
        when(INTEGERS_STATISTIC_MOCK.getNumberOfLines()).thenReturn(0);
        when(STRINGS_STATISTIC_MOCK.getNumberOfLines()).thenReturn(0);
    }
}
