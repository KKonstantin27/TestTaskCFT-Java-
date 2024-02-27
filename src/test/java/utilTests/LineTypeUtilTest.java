package utilTests;

import enums.TypeOfLine;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import utils.LineTypeUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineTypeUtilTest {
    private final LineTypeUtil lineTypeUtil = new LineTypeUtil();

    @Test
    @Order(1)
    public void floatsLineShouldReturnFloatsType() {
        assertEquals(TypeOfLine.FLOAT, lineTypeUtil.defineTypeOfLine("3.1415"));
        assertEquals(TypeOfLine.FLOAT, lineTypeUtil.defineTypeOfLine(".1415"));
        assertEquals(TypeOfLine.FLOAT, lineTypeUtil.defineTypeOfLine("1.528535047E-25"));
        assertEquals(TypeOfLine.FLOAT, lineTypeUtil.defineTypeOfLine("-1.528535047E-25"));
        assertEquals(TypeOfLine.FLOAT, lineTypeUtil.defineTypeOfLine("0.0"));
    }

    @Test
    @Order(2)
    public void integersLineShouldReturnIntegersType() {
        assertEquals(TypeOfLine.INTEGER, lineTypeUtil.defineTypeOfLine("1234567890123456789"));
        assertEquals(TypeOfLine.INTEGER, lineTypeUtil.defineTypeOfLine("-1234567890123456789"));
        assertEquals(TypeOfLine.INTEGER, lineTypeUtil.defineTypeOfLine("12345"));
        assertEquals(TypeOfLine.INTEGER, lineTypeUtil.defineTypeOfLine("0"));
    }

    @Test
    @Order(3)
    public void stringsLineShouldReturnStringsType() {
        assertEquals(TypeOfLine.STRING, lineTypeUtil.defineTypeOfLine("Test string"));
        assertEquals(TypeOfLine.STRING, lineTypeUtil.defineTypeOfLine("Тестовая строка"));
        assertEquals(TypeOfLine.STRING, lineTypeUtil.defineTypeOfLine(" "));
        assertEquals(TypeOfLine.STRING, lineTypeUtil.defineTypeOfLine("/?{"));
        assertEquals(TypeOfLine.STRING, lineTypeUtil.defineTypeOfLine("."));
    }
}
