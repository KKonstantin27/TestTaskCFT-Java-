import exceptions.InvalidArgumentsException;
import services.ReadingWritingService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final Scanner CONSOLE = new Scanner(System.in);
    private static final ReadingWritingService READING_WRITING_SERVICE = new ReadingWritingService();
    public static void main(String[] args) {
        try {
            String[] writingFileNames = new String[]{"floats.txt", "integers.txt", "strings.txt"};
            Path[] writingPaths = new Path[3];
            List<Path> readingPaths = new ArrayList<>();
            boolean isRewriteBehavior = true;
            boolean isThereStatistic = false;
            boolean isFullStatistic = false;
            Path pathToJar = Paths.get(App.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            Path jarParentDir = pathToJar.getParent();
            Arrays.fill(writingPaths, jarParentDir);

            if (args.length == 0) {
                throw new InvalidArgumentsException();
            }

            for(int i = 0; i < args.length; i++) {
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
                        if (args[i].endsWith(".txt")) {
                            readingPaths.add(jarParentDir.resolve(args[i]));
                        } else {
                           throw new InvalidArgumentsException();
                        }
                }
            }

            for (int i = 0; i < writingPaths.length; i++) {
                writingPaths[i] = writingPaths[i].resolve(writingFileNames[i]);
            }
            READING_WRITING_SERVICE.readWriteToFiles(readingPaths, writingPaths, isRewriteBehavior, isThereStatistic, isFullStatistic);
        } catch (URISyntaxException | IOException e) {
            handleURISyntaxIOExceptions();
        }
    }
    public static void handleURISyntaxIOExceptions() {
        System.out.println(
                """
                        Введены неверные аргументы для работы программы.\s
                        ------------------------------\s
                        Возможные аргументы:\s
                        -o /example/path (необязательный аргумент, указывающий путь к выходным файлам относительно jar файла.
                        Для указания в пути родительских папок используются "..". Например /../example/path)\s
                        -p result_ (необязательный аргумент, указывающий префикс выходных файлов)\s
                        -a (необязательный аргумент, при вводе данного аргумента выходные файлы не перезаписываются(если
                        выходные файлы с такими именами существуют), а новые строки добавляются в конец файлов)\s
                        -s (необязательный аргумент, при вводе данного аргумента выводит краткую статистику в консоль)\s
                        -f (необязательный аргумент, при вводе данного аргумента выводит подробную статистику в консоль)\s
                        example1.txt (обязательный аргумент, указывающий на имя входного файла в формате txt.
                        Файл должен находиться в папке с jar файлом. Можно указать несколько входных файлов через пробел)\s
                        ------------------------------\s
                        Пример введеных аргументов: -f -a -o /example/path -p sample- in1.txt in2.txt\s
                        ------------------------------\s
                        Пожалуйста, повторите ввод аргументов (одной строкой, разделяя аргументы пробелами, как в примере):\s
                        """);

        String userInput = CONSOLE.nextLine();
        try {
            main(userInput.split(" "));
        } catch (StackOverflowError e) {
            System.err.println(
                    """
                       Произошла ошибка переполнения стека (StackOverflowError). Ошибка возникла из-за множественного вызова методов
                       в связи с многократным неверным вводом аргументов пользователем.\s
                       Работа программы остановлена.\s
                            """);
        }
    }
}
