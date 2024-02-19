package services.statisticsServices;

import lombok.Getter;
import utils.SizeUnitUtil;

@Getter
public class StringsStatisticsService extends BaseStatisticsService {
    private int shortestLineLength = Integer.MAX_VALUE;
    private long shortestLineSize;
    private int longestLineLength = Integer.MIN_VALUE;
    private long longestLineSize;

    public void calculateStatistics(String line, boolean isFullStatistics) {
        numberOfLines++;
        if (isFullStatistics) {
            if (line.length() < shortestLineLength) {
                shortestLineLength = line.length();
                shortestLineSize = line.getBytes().length;
            }
            if (line.length() > longestLineLength) {
                longestLineLength = line.length();
                longestLineSize = line.getBytes().length;
            }
        }
    }

    public void printStatistics(boolean isFullStatistics) {
        df.setMaximumFractionDigits(2);
        System.out.println("Количество строк: " + df.format(numberOfLines));
        if (isFullStatistics) {
            SizeUnitUtil sizeUnitUtil = new SizeUnitUtil();
            System.out.println("Размер самой короткой строки: " + sizeUnitUtil.convertToBiggerUnits(shortestLineSize));
            System.out.println("Размер самой длинной строки: " + sizeUnitUtil.convertToBiggerUnits(longestLineSize));
            System.out.println("--------------------------------------------------");
        }
    }
}
