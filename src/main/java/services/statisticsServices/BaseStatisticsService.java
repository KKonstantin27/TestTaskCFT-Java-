package services.statisticsServices;

import java.text.DecimalFormat;

public abstract class BaseStatisticsService {
    protected int numberOfLines = 0;
    protected DecimalFormat df = new DecimalFormat();
    public abstract void calculateStatistics(String line, boolean isFullStatistics);
    public abstract void printStatistics(boolean isFullStatistics);
}
