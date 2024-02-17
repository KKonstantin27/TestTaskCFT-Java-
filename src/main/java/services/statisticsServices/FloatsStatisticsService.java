package services.statisticsServices;

import java.math.BigDecimal;

public class FloatsStatisticsService extends BaseStatisticsService {
    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;
    private double avg;
    private double sum;

    public void calculateStatistics(String line, boolean isFullStatistics) {
        double currentNumber = Double.parseDouble(line);
        numberOfLines++;
        if (isFullStatistics) {
            sum += currentNumber;
            min = Math.min(currentNumber, min);
            max = Math.max(currentNumber, max);
            avg = sum / numberOfLines;
        }
    }

    public void printStatistics(boolean isFullStatistics) {
        df.setMaximumFractionDigits(30);
        System.out.println("Количество вещественных чисел: " + df.format(numberOfLines));
        if (isFullStatistics) {
            System.out.println("Минимальное вещественное число: " + df.format(min));
            System.out.println("Максимальное вещественное число: " + df.format(max));
            System.out.println("Среднее вещественных чисел: " + df.format(avg));
            System.out.println("Сумма вещественных чисел: " + df.format(sum));
            System.out.println("--------------------------------------------------");
        }
    }
}
