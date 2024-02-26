import lombok.Getter;
import lombok.Setter;
import services.ReadingWritingService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    @Setter
    private static ReadingWritingService readingWritingService = new ReadingWritingService();
    @Setter
    private static ExceptionsHandler exceptionsHandler = new ExceptionsHandler();
    @Getter
    private static List<Path> readingPaths;
    @Getter
    private static String[] writingFileNames;
    @Getter
    private static Path[] writingPaths;
    @Getter
    private static boolean isRewritingBehavior;
    @Getter
    private static boolean isThereStatistic;
    @Getter
    private static boolean isFullStatistic;

    public static void main(String[] args) {
        try {
            Path jarPath = Paths.get("").toAbsolutePath();

            readingPaths = new ArrayList<>();
            writingFileNames = new String[]{"floats.txt", "integers.txt", "strings.txt"};
            writingPaths = new Path[3];
            isRewritingBehavior = true;
            isThereStatistic = false;
            isFullStatistic = false;

            Arrays.fill(writingPaths, jarPath);

            if (args.length == 0) {
                throw new IllegalArgumentException();
            }

            for (int i = 0; i < args.length; i++) {
                int nextIndex = i + 1;
                switch (args[i]) {
                    case "-o":
                        Arrays.fill(writingPaths, jarPath.resolve(Paths.get(args[nextIndex])));
                        i++;
                        break;
                    case "-p":
                        writingFileNames = Arrays.stream(writingFileNames)
                                .map(x -> args[nextIndex] + x)
                                .toArray(String[]::new);
                        i++;
                        break;
                    case "-a":
                        isRewritingBehavior = false;
                        break;
                    case "-s":
                        isThereStatistic = true;
                        break;
                    case "-f":
                        isThereStatistic = true;
                        isFullStatistic = true;
                        break;
                    default:
                        if (args[i].endsWith(".txt")) {
                            readingPaths.add(jarPath.resolve(args[i]));
                        } else {
                            throw new IllegalArgumentException();
                        }
                }
            }
            for (int i = 0; i < writingPaths.length; i++) {
                writingPaths[i] = writingPaths[i].resolve(writingFileNames[i]);
            }
            readingWritingService.readWriteToFiles(readingPaths, writingPaths, isRewritingBehavior, isThereStatistic, isFullStatistic);
        } catch (IOException | IllegalArgumentException e) {
            exceptionsHandler.handleIOInvalidPathExceptions();
        }
    }
}
