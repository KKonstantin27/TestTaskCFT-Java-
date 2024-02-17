package services.statisticsServices;

public class IntegersStatisticsService extends BaseStatisticsService {
    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;
    private double avg;
    private long sum;

    public void calculateStatistics(String line, boolean isFullStatistics) {
        long currentNumber = Long.parseLong(line);
        numberOfLines++;
        if (isFullStatistics) {
            sum += currentNumber;
            min = Math.min(currentNumber, min);
            max = Math.max(currentNumber, max);
            avg = (double) sum / numberOfLines;
        }
    }
    public void printStatistics(boolean isFullStatistics) {
        df.setMaximumFractionDigits(2);
        System.out.println("Количество целых чисел: " + df.format(numberOfLines));
        if (isFullStatistics) {
            System.out.println("Минимальное целое число: " + df.format(min));
            System.out.println("Максимальное целое число: " + df.format(max));
            System.out.println("Среднее целых чисел: " + df.format(avg));
            System.out.println("Сумма целых чисел: " + df.format(sum));
            System.out.println("--------------------------------------------------");
        }
    }
}
