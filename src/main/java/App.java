import exceptions.InvalidArgumentsException;
import lombok.Getter;
import lombok.Setter;
import services.ReadingWritingService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    @Setter
    private static ReadingWritingService readingWritingService = new ReadingWritingService();
    @Getter
    private static String[] writingFileNames;
    @Getter
    private static Path[] writingPaths;
    @Getter
    private static List<Path> readingPaths;
    @Getter
    private static boolean isRewritingBehavior;
    @Getter
    private static boolean isThereStatistic;
    @Getter
    private static boolean isFullStatistic;
    @Getter
    @Setter
    private static ExceptionHandler exceptionHandler = new ExceptionHandler();

    public static void main(String[] args) {
        try {
            Path pathToJar = Paths.get(App.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            Path jarParentDir = pathToJar.getParent();

            writingFileNames = new String[]{"floats.txt", "integers.txt", "strings.txt"};
            writingPaths = new Path[3];
            readingPaths = new ArrayList<>();
            isRewritingBehavior = true;
            isThereStatistic = false;
            isFullStatistic = false;

            Arrays.fill(writingPaths, jarParentDir);

            if (args.length == 0) {
                throw new InvalidArgumentsException();
            }

            for (int i = 0; i < args.length; i++) {
                int nextIndex = i + 1;
                switch (args[i]) {
                    case "-o":
                        String writingPath = args[nextIndex].charAt(0) == '/' ? args[nextIndex].substring(1) : args[nextIndex];
                        Arrays.fill(writingPaths, jarParentDir.resolve(Paths.get(writingPath)));
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
                            readingPaths.add(jarParentDir.resolve(args[i]));
                        } else {
                            throw new IllegalArgumentException();
                        }
                }
            }

            for (int i = 0; i < writingPaths.length; i++) {
                writingPaths[i] = writingPaths[i].resolve(writingFileNames[i]);
            }
            readingWritingService.readWriteToFiles(readingPaths, writingPaths, isRewritingBehavior, isThereStatistic, isFullStatistic);
        } catch (URISyntaxException | IOException | IllegalArgumentException e) {
            exceptionHandler.handleURISyntaxIOInvalidPathExceptions();
        }
    }
}
