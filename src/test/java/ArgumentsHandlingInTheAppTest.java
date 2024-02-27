import enums.TypeOfLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
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
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.verify;

public class ArgumentsHandlingInTheAppTest {
    private static String[] expectedWritingFileNames;
    private static Path[] expectedWritingPaths;
    private static List<Path> expectedReadingPaths;
    private static Path jarPath;
    private static final ReadingWritingService READING_WRITING_SERVICE_MOCK = Mockito.mock(ReadingWritingService.class);
    private static final ExceptionsHandler EXCEPTIONS_HANDLER_MOCK = Mockito.mock(ExceptionsHandler.class);

    @BeforeEach
    public void prepareTestEnvironment() {
        jarPath = Paths.get("").toAbsolutePath();

        expectedWritingFileNames = new String[]{"floats.txt", "integers.txt", "strings.txt"};
        expectedWritingPaths = new Path[TypeOfLine.values().length];
        expectedReadingPaths = new ArrayList<>();

        Arrays.fill(expectedWritingPaths, jarPath);
        expectedReadingPaths.add(jarPath.resolve("in1.txt"));
        clearInvocations(READING_WRITING_SERVICE_MOCK, EXCEPTIONS_HANDLER_MOCK);
    }

    @Test
    @Order(1)
    public void testOnlyInputFilesArguments() {
        App.setReadingWritingService(READING_WRITING_SERVICE_MOCK);
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
    @Order(2)
    public void test_o_a_arguments() {
        App.setReadingWritingService(READING_WRITING_SERVICE_MOCK);
        String[] args = {"-a", "-o", "example/path", "in1.txt"};
        App.main(args);

        Arrays.fill(expectedWritingPaths, jarPath.resolve(args[2]));
        buildExpectedWritingPaths();

        assertArrayEquals(expectedWritingFileNames, App.getWritingFileNames());
        assertArrayEquals(expectedWritingPaths, App.getWritingPaths());
        assertEquals(expectedReadingPaths, App.getReadingPaths());
        assertFalse(App.isRewritingBehavior());
        assertFalse(App.isThereStatistic());
        assertFalse(App.isFullStatistic());
    }

    @Test
    @Order(3)
    public void test_s_p_arguments() {
        App.setReadingWritingService(READING_WRITING_SERVICE_MOCK);
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
    @Order(4)
    public void test_f_o_p_a_arguments() {
        App.setReadingWritingService(READING_WRITING_SERVICE_MOCK);
        String[] args = {"-a", "-f", "-o", "example/path", "-p", "sample-", "in1.txt"};
        App.main(args);

        Arrays.fill(expectedWritingPaths, jarPath.resolve(args[3]));

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
    @Order(5)
    public void inputFileDoesNotExistShouldInvokeHandlingMethod() {
        App.setReadingWritingService(new ReadingWritingService());
        App.setExceptionsHandler(EXCEPTIONS_HANDLER_MOCK);
        Random random = new Random();

        String[] args = {"non-existent-file.txt" + random.nextInt(100000),
                "non-existent-file.txt" + random.nextInt(100000)};
        App.main(args);

        verify(EXCEPTIONS_HANDLER_MOCK).handleIOIllegalArgumentExceptions();
    }

    @Test
    @Order(6)
    public void invalidArgumentsShouldInvokeHandlingMethod() {
        App.setReadingWritingService(new ReadingWritingService());
        App.setExceptionsHandler(EXCEPTIONS_HANDLER_MOCK);
        String[] args = {"-x", "in1", "in1.txt"};
        App.main(args);
        verify(EXCEPTIONS_HANDLER_MOCK).handleIOIllegalArgumentExceptions();
    }

    @Test
    @Order(7)
    public void emptyArgumentsShouldInvokeHandlingMethod() {
        App.setReadingWritingService(new ReadingWritingService());
        App.setExceptionsHandler(EXCEPTIONS_HANDLER_MOCK);
        String[] args = {};
        App.main(args);
        verify(EXCEPTIONS_HANDLER_MOCK).handleIOIllegalArgumentExceptions();
    }

    private static void buildExpectedWritingPaths() {
        for (int i = 0; i < expectedWritingPaths.length; i++) {
            expectedWritingPaths[i] = expectedWritingPaths[i].resolve(expectedWritingFileNames[i]);
        }
    }
}
