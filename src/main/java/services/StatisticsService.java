package services;

import entities.FloatsStatistic;
import entities.IntegersStatistic;
import entities.StringsStatistic;
import enums.TypeOfLine;
import lombok.Setter;

import java.util.stream.Stream;

@Setter
public class StatisticsService {
    private FloatsStatistic floatsStatistic = new FloatsStatistic();
    private IntegersStatistic integersStatistic = new IntegersStatistic();
    private StringsStatistic stringsStatistic = new StringsStatistic();

    public void calculateStatistics(String line, boolean isFullStatistics, TypeOfLine typeOfLine) {
        switch (typeOfLine) {
            case FLOAT -> floatsStatistic.calculateStatistics(line, isFullStatistics);
            case INTEGER -> integersStatistic.calculateStatistics(line, isFullStatistics);
            case STRING -> stringsStatistic.calculateStatistics(line, isFullStatistics);
        }
    }

    public void printStatistics(boolean isThereStatistics, boolean isFullStatistics) {
        if (isThereStatistics) {
            Stream.of(floatsStatistic, integersStatistic, stringsStatistic)
                    .filter(x -> x.getNumberOfLines() > 0)
                    .forEach(x -> x.printStatistics(isFullStatistics));
        }
    }

    public int getNumberOfLinesOfThisType(TypeOfLine typeOfLine) {
        int result = -1;
        switch (typeOfLine) {
            case FLOAT -> result = floatsStatistic.getNumberOfLines();
            case INTEGER -> result = integersStatistic.getNumberOfLines();
            case STRING -> result = stringsStatistic.getNumberOfLines();
        }
        return result;
    }
}
