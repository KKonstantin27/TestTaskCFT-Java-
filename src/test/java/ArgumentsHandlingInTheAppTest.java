import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import services.ReadingWritingService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class ArgumentsHandlingInTheAppTest {
    private static String[] expectedWritingFileNames;
    private static Path[] expectedWritingPaths = new Path[3];
    private static List<Path> expectedReadingPaths = new ArrayList<>();
    private static Path jarParentDir;
    ReadingWritingService readingWritingServiceMock = Mockito.mock(ReadingWritingService.class);
    ExceptionsHandler exceptionsHandlerMock = Mockito.mock(ExceptionsHandler.class);

    @BeforeEach
    public void prepareTestEnvironment() throws Exception {
        Path pathToJar = Paths.get(App.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        jarParentDir = pathToJar.getParent();

        expectedWritingFileNames = new String[]{"floats.txt", "integers.txt", "strings.txt"};
        expectedWritingPaths = new Path[3];
        expectedReadingPaths = new ArrayList<>();

        Arrays.fill(expectedWritingPaths, jarParentDir);
        expectedReadingPaths.add(jarParentDir.resolve("in1.txt"));
    }

    @Test
    public void testOnlyInputFilesArguments() {
        App.setReadingWritingService(readingWritingServiceMock);
        String[] args = {"in1.txt"};
        App.main(args);

        buildExpectedWritingPaths();

        assertArrayEquals(expectedWritingFileNames, App.getWritingFileNames());
        assertArrayEquals(expectedWritingPaths, App.getWritingPaths());
        assertEquals(expectedReadingPaths, App.getReadingPaths());
        assertTrue(App.isRewritingBehavior());
        assertFalse(App.isThereStatistic());
        assertFalse(App.isFullStatistic());
    }

    @Test
    public void test_o_a_arguments() {
        App.setReadingWritingService(readingWritingServiceMock);
        String[] args = {"-a", "-o", "/example/path", "in1.txt"};
        App.main(args);

        Arrays.fill(expectedWritingPaths, jarParentDir.resolve(args[2].substring(1)));
        buildExpectedWritingPaths();

        assertArrayEquals(expectedWritingFileNames, App.getWritingFileNames());
        assertArrayEquals(expectedWritingPaths, App.getWritingPaths());
        assertEquals(expectedReadingPaths, App.getReadingPaths());
        assertFalse(App.isRewritingBehavior());
        assertFalse(App.isThereStatistic());
        assertFalse(App.isFullStatistic());
    }

    @Test
    public void test_s_p_arguments() {
        App.setReadingWritingService(readingWritingServiceMock);
        String[] args = {"-s", "-p", "sample-", "in1.txt"};
        App.main(args);

        expectedWritingFileNames = Arrays.stream(expectedWritingFileNames)
                .map(x -> args[2] + x)
                .toArray(String[]::new);

        buildExpectedWritingPaths();

        assertArrayEquals(expectedWritingFileNames, App.getWritingFileNames());
        assertArrayEquals(expectedWritingPaths, App.getWritingPaths());
        assertEquals(expectedReadingPaths, App.getReadingPaths());
        assertTrue(App.isRewritingBehavior());
        assertTrue(App.isThereStatistic());
        assertFalse(App.isFullStatistic());
    }

    @Test
    public void test_f_o_p_a_arguments() {
        App.setReadingWritingService(readingWritingServiceMock);
        String[] args = {"-a", "-f", "-o", "/example/path", "-p", "sample-", "in1.txt"};
        App.main(args);

        Arrays.fill(expectedWritingPaths, jarParentDir.resolve(args[3].substring(1)));

        expectedWritingFileNames = Arrays.stream(expectedWritingFileNames)
                .map(x -> args[5] + x)
                .toArray(String[]::new);

        buildExpectedWritingPaths();

        assertArrayEquals(expectedWritingFileNames, App.getWritingFileNames());
        assertArrayEquals(expectedWritingPaths, App.getWritingPaths());
        assertEquals(expectedReadingPaths, App.getReadingPaths());
        assertFalse(App.isRewritingBehavior());
        assertTrue(App.isThereStatistic());
        assertTrue(App.isFullStatistic());
    }

    @Test
    public void inputFileDoesNotExistShouldCallHandlingMethod() {
        App.setReadingWritingService(new ReadingWritingService());
        Random random = new Random();
        String[] args = {"non-existent-file.txt" + random.nextInt(100000),
                "non-existent-file.txt" + random.nextInt(100000)};
        App.setExceptionsHandler(exceptionsHandlerMock);
        App.main(args);
        verify(exceptionsHandlerMock).handleURISyntaxIOInvalidPathExceptions();
    }


    @Test
    public void invalidPathShouldCallHandlingMethod() {
        App.setReadingWritingService(new ReadingWritingService());
        String[] args = {"-o", "/example/path\"", "in1.txt"};
        App.setExceptionsHandler(exceptionsHandlerMock);
        App.main(args);
        verify(exceptionsHandlerMock).handleURISyntaxIOInvalidPathExceptions();
    }

    @Test
    public void invalidArgumentsShouldCallHandlingMethod() {
        App.setReadingWritingService(new ReadingWritingService());
        String[] args = {"-x", "in1", "in1.txt"};
        App.setExceptionsHandler(exceptionsHandlerMock);
        App.main(args);
        verify(exceptionsHandlerMock).handleURISyntaxIOInvalidPathExceptions();

    }

    @Test
    public void emptyArgumentsShouldCallHandlingMethod() {
        App.setReadingWritingService(new ReadingWritingService());
        String[] args = {};
        App.setExceptionsHandler(exceptionsHandlerMock);
        App.main(args);
        verify(exceptionsHandlerMock).handleURISyntaxIOInvalidPathExceptions();
    }

    private static void buildExpectedWritingPaths() {
        for (int i = 0; i < expectedWritingPaths.length; i++) {
            expectedWritingPaths[i] = expectedWritingPaths[i].resolve(expectedWritingFileNames[i]);
        }
    }
}
