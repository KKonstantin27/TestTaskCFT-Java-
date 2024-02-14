import java.nio.file.Path;
import java.util.Arrays;

public class Util {

    private static StringBuilder[] paths = new StringBuilder[3];
    private static boolean isRewriteBehavior = true;
    private static boolean isPrintStatistics = false;
    private static boolean isFullStatistics = false;
    private static final String PATH_DELIMITER = "/";

    public static void main(String[] args) {

        for(int i = 0; i < args.length; i++) {
            int nextIndex = i + 1;
            switch (args[i]) {
                case "-o":
                    StringBuilder path = new StringBuilder(args[nextIndex]);
                    Arrays.fill(paths, path);
                    break;
                case "-p":
                    Arrays.stream(paths).forEach(x -> x.insert(0, args[nextIndex] + PATH_DELIMITER));
                    break;
                case "-a":
                    isRewriteBehavior = false;
                case "-s":
                    isPrintStatistics = true;
                    break;
                case "-f":
                    isPrintStatistics = true;
                    isFullStatistics = true;
                    break;
            }
        }
    }
}
