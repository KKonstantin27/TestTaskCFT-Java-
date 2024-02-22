package entities;

import lombok.Getter;

import java.text.DecimalFormat;
@Getter
public abstract class BaseStatistics {

    protected int numberOfLines = 0;

    protected DecimalFormat df = new DecimalFormat();

    public abstract void calculateStatistics(String line, boolean isFullStatistics);

    public abstract void printStatistics(boolean isFullStatistics);
}
