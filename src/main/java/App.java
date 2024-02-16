import services.ReadingWritingService;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    private static final String PATH_DELIMITER = "/";
    private static String[] defaultWritingFileNames = {"floats.txt", "integers.txt", "strings.txt"};
    private static ReadingWritingService readingWritingService = new ReadingWritingService();
    private static Path[] writingPaths = new Path[3];
    private static List<Path> readingPaths = new ArrayList<>();
    private static Path pathToJar;
    private static Path jarParentDir;
    private static boolean isRewriteBehavior = true;
    private static boolean isThereStatistic = false;
    private static boolean isFullStatistic = false;
    public static void main(String[] args) throws URISyntaxException {
        args = new String[]{"-f", "-p", "sample-", "in1.txt", "in2.txt"};
        final String[] asd = args;

        pathToJar = Paths.get(App.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        jarParentDir = pathToJar.getParent();
        Arrays.fill(writingPaths, jarParentDir);

        for(int i = 0; i < asd.length; i++) {
            int nextIndex = i + 1;
            switch (args[i]) {
                case "-o":
                    Arrays.fill(writingPaths, Paths.get(asd[nextIndex]));
                    i++;
                    break;
                case "-p":
                    defaultWritingFileNames = Arrays.stream(defaultWritingFileNames)
                            .map(x -> asd[nextIndex] + x)
                            .toArray(String[]::new);
                    i++;
                    break;
                case "-a":
                    isRewriteBehavior = false;
                    break;
                case "-s":
                    isThereStatistic = true;
                    break;
                case "-f":
                    isThereStatistic = true;
                    isFullStatistic = true;
                    break;
                default:
                    if (asd[i].contains("txt")) {
                        readingPaths.add(jarParentDir.resolve(asd[i]));
                    } else {
                        throw new RuntimeException();
                    }
            }
        }

        for (int i = 0; i < writingPaths.length; i++) {
            writingPaths[i] = writingPaths[i].resolve(defaultWritingFileNames[i]);
        }

        readingWritingService.readWriteToFiles(readingPaths, writingPaths, isRewriteBehavior, isThereStatistic, isFullStatistic);
    }
//    public static void asd() throws URISyntaxException {
//        System.out.println("Invalid program's arguments. Please input correct arguments (example: -f -p sample- in1.txt in2.txt):");
//        Scanner console = new Scanner(System.in);
//        String userInput = console.nextLine();
//        main(userInput.split(" "));
//    }
}
