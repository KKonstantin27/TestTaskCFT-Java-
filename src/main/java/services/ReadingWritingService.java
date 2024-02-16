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
    private boolean isFloatsFileEmpty = false;
    private boolean isIntegersFileEmpty = false;
    private boolean isStringsFileEmpty = false;

    public void readWriteToFiles(List<Path> readingPaths, Path[] writingPaths, boolean isRewriteBehavior,
                                 boolean isThereStatistics, boolean isFullStatistics) throws IOException {

        LineTypeUtil lineTypeUtil = new LineTypeUtil();
        FloatsStatisticsService floatsStatisticsService = new FloatsStatisticsService();
        IntegersStatisticsService integersStatisticsService = new IntegersStatisticsService();
        StringsStatisticsService stringsStatisticsService = new StringsStatisticsService();

        prepareWritingFiles(writingPaths, isRewriteBehavior);

        try (BufferedWriter bufferedFloatsWriter = new BufferedWriter(new FileWriter(writingPaths[0].toString(), true));
             BufferedWriter bufferedIntegersWriter = new BufferedWriter(new FileWriter(writingPaths[1].toString(), true));
             BufferedWriter bufferedStringsWriter = new BufferedWriter(new FileWriter(writingPaths[2].toString(), true))) {

            for (Path currentReadingPath : readingPaths) {
                try (BufferedReader bufferedReader = Files.newBufferedReader(currentReadingPath)) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {

                        if (line.equals("")) {
                            continue;
                        }

                        if (lineTypeUtil.isFloats(line)) {
                            bufferedFloatsWriter.write(line + "\n");
                            if (isThereStatistics) {
                                floatsStatisticsService.calculateStatistics(line, isFullStatistics);
                            }

                        } else if (lineTypeUtil.isInteger(line)) {
                            bufferedIntegersWriter.write(line + "\n");
                            if (isThereStatistics) {
                                integersStatisticsService.calculateStatistics(line, isFullStatistics);
                            }

                        } else {
                            bufferedStringsWriter.write(line + "\n");
                            if (isThereStatistics) {
                                stringsStatisticsService.calculateStatistics(line, isFullStatistics);
                            }
                        }
                    }
                }
            }
        }

        deleteEmptyWritingFiles(writingPaths);

        if (isThereStatistics) {
            if (!isFloatsFileEmpty) {
                floatsStatisticsService.printStatistics(isFullStatistics);
            }
            if (!isIntegersFileEmpty) {
                integersStatisticsService.printStatistics(isFullStatistics);
            }
            if (!isStringsFileEmpty) {
                stringsStatisticsService.printStatistics(isFullStatistics);
            }
        }
    }

    private void prepareWritingFiles(Path[] writingPaths, boolean isRewriteBehavior) throws IOException {
        if (isRewriteBehavior) {
            deleteWritingFiles(writingPaths);
        }
        createWritingFiles(writingPaths);
    }

    private void createWritingFiles(Path[] writingPaths) throws IOException {
        for (Path writingPath : writingPaths) {
            if (Files.notExists(writingPath)) {
                Files.createFile(writingPath);
            }
        }
    }

    private void deleteWritingFiles(Path[] writingPaths) throws IOException {
        for (Path writingPath : writingPaths) {
            Files.deleteIfExists(writingPath);
        }
    }

    private void deleteEmptyWritingFiles(Path[] writingPaths) throws IOException {
        for (int i = 0; i < writingPaths.length; i++) {
            if (Files.size(writingPaths[i]) == 0) {
                Files.delete(writingPaths[i]);
                switch (i) {
                    case 0 -> isFloatsFileEmpty = true;
                    case 1 -> isIntegersFileEmpty = true;
                    case 2 -> isStringsFileEmpty = true;
                }
            }
        }
    }
}
