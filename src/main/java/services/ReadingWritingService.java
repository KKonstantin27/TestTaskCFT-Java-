package services;

import enums.TypeOfLine;
import utils.LineTypeUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReadingWritingService {
    private final StatisticsService statisticsService = new StatisticsService();

    public void readWriteToFiles(List<Path> readingPaths, Path[] writingPaths, boolean isRewritingBehavior,
                                 boolean isThereStatistics, boolean isFullStatistics) throws IOException {

        LineTypeUtil lineTypeUtil = new LineTypeUtil();
        for (Path currentReadingPath : readingPaths) {
            try (BufferedReader bufferedReader = Files.newBufferedReader(currentReadingPath)) {
                String line;
                TypeOfLine typeOfLine;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.equals("")) {
                        continue;
                    }
                    typeOfLine = lineTypeUtil.defineTypeOfLine(line);
                    writeLine(writingPaths[typeOfLine.getPathIndex()], line, isRewritingBehavior, typeOfLine);
                    statisticsService.calculateStatistics(line, isFullStatistics, typeOfLine);
                }
            }
        }
        statisticsService.printStatistics(isThereStatistics, isFullStatistics);
    }

    private void prepareWritingFile(Path writingPath, boolean isRewritingBehavior) throws IOException {
        if (isRewritingBehavior) {
            Files.deleteIfExists(writingPath);
        }
        Files.createDirectories(writingPath.getParent());
    }

    private void writeLine(Path writingPath, String line, boolean isRewritingBehavior, TypeOfLine typeOfLine) throws IOException {
        if (statisticsService.getNumberOfLinesOfThisType(typeOfLine) == 0) {
            prepareWritingFile(writingPath, isRewritingBehavior);
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(writingPath.toString(), true))) {
            bufferedWriter.write(line + "\n");
        }
    }
}
