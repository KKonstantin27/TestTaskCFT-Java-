package services;

import services.statisticsServices.FloatsStatisticsService;
import services.statisticsServices.IntegersStatisticsService;
import services.statisticsServices.StringsStatisticsService;
import utils.LineTypeUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReadingWritingService {
    private BufferedWriter bufferedFloatsWriter;
    private BufferedWriter bufferedIntegersWriter;
    private BufferedWriter bufferedStringsWriter;
    private boolean areThereFloatsInTheFile = false;
    private boolean areThereIntegersInTheFile = false;
    private boolean areThereStringsInTheFile = false;
    private final FloatsStatisticsService floatsStatisticsService = new FloatsStatisticsService();
    private final IntegersStatisticsService integersStatisticsService = new IntegersStatisticsService();
    private final StringsStatisticsService stringsStatisticsService = new StringsStatisticsService();

    public void readWriteToFiles(List<Path> readingPaths, Path[] writingPaths, boolean isRewritingBehavior,
                                 boolean isThereStatistics, boolean isFullStatistics) throws IOException {

        LineTypeUtil lineTypeUtil = new LineTypeUtil();

        try {
            for (Path currentReadingPath : readingPaths) {
                try (BufferedReader bufferedReader = Files.newBufferedReader(currentReadingPath)) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.equals("")) {
                            continue;
                        }
                        if (lineTypeUtil.isFloat(line)) {
                            writeFloatsLine(writingPaths, line, isRewritingBehavior, isThereStatistics, isFullStatistics);
                        } else if (lineTypeUtil.isInteger(line)) {
                            writeIntegersLine(writingPaths, line, isRewritingBehavior, isThereStatistics, isFullStatistics);
                        } else {
                            writeStringsLine(writingPaths, line, isRewritingBehavior, isThereStatistics, isFullStatistics);
                        }
                    }
                }
            }
        } finally {
            releaseOutputStreams();
        }

        if (isThereStatistics) {
            printStatistics(isFullStatistics);
        }
    }

    private void prepareWritingFile(Path writingPath, boolean isRewritingBehavior) throws IOException {
        if (isRewritingBehavior) {
            Files.deleteIfExists(writingPath);
        }
        Files.createDirectories(writingPath.getParent());
    }

    private void releaseOutputStreams() throws IOException {
        if (areThereFloatsInTheFile) {
            areThereFloatsInTheFile = false;
            bufferedFloatsWriter.close();
        }

        if (areThereIntegersInTheFile) {
            areThereIntegersInTheFile = false;
            bufferedIntegersWriter.close();
        }

        if (areThereStringsInTheFile) {
            areThereStringsInTheFile = false;
            bufferedStringsWriter.close();
        }
    }

    private void printStatistics(boolean isFullStatistics) {
        if (areThereFloatsInTheFile) {
            floatsStatisticsService.printStatistics(isFullStatistics);
        }

        if (areThereIntegersInTheFile) {
            integersStatisticsService.printStatistics(isFullStatistics);
        }

        if (areThereStringsInTheFile) {
            stringsStatisticsService.printStatistics(isFullStatistics);
        }
    }

    private void writeFloatsLine(Path[] writingPaths, String line, boolean isRewritingBehavior, boolean isThereStatistics,
                                 boolean isFullStatistics) throws IOException {

        if (!areThereFloatsInTheFile) {
            areThereFloatsInTheFile = true;
            prepareWritingFile(writingPaths[0], isRewritingBehavior);
            bufferedFloatsWriter = new BufferedWriter(new FileWriter(writingPaths[0].toString(), true));
        }

        bufferedFloatsWriter.write(line + "\n");

        if (isThereStatistics) {
            floatsStatisticsService.calculateStatistics(line, isFullStatistics);
        }
    }

    private void writeIntegersLine(Path[] writingPaths, String line, boolean isRewritingBehavior, boolean isThereStatistics,
                                   boolean isFullStatistics) throws IOException {

        if (!areThereIntegersInTheFile) {
            areThereIntegersInTheFile = true;
            prepareWritingFile(writingPaths[1], isRewritingBehavior);
            bufferedIntegersWriter = new BufferedWriter(new FileWriter(writingPaths[1].toString(), true));
        }

        bufferedIntegersWriter.write(line + "\n");

        if (isThereStatistics) {
            integersStatisticsService.calculateStatistics(line, isFullStatistics);
        }
    }

    private void writeStringsLine(Path[] writingPaths, String line, boolean isRewritingBehavior, boolean isThereStatistics,
                                  boolean isFullStatistics) throws IOException {

        if (!areThereStringsInTheFile) {
            areThereStringsInTheFile = true;
            prepareWritingFile(writingPaths[2], isRewritingBehavior);
            bufferedStringsWriter = new BufferedWriter(new FileWriter(writingPaths[2].toString(), true));
        }

        bufferedStringsWriter.write(line + "\n");

        if (isThereStatistics) {
            stringsStatisticsService.calculateStatistics(line, isFullStatistics);
        }
    }
}
