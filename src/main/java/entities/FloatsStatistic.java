package entities;

import lombok.Getter;

@Getter
public class FloatsStatistic extends BaseStatistic {
    private double min = Double.MAX_VALUE;
    private double max = Double.NEGATIVE_INFINITY;
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
