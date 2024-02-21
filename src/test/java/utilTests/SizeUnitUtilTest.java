package utilTests;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import utils.SizeUnitUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SizeUnitUtilTest {
    private final SizeUnitUtil sizeUnitUtil = new SizeUnitUtil();

    @Test
    @Order(1)
    public void convertToBiggerUnitsShouldReturnRightString() {
        assertEquals("0 Б", sizeUnitUtil.convertToBiggerUnits(0));
        assertEquals("123 Б", sizeUnitUtil.convertToBiggerUnits(123));
        assertEquals("12,06 КБ", sizeUnitUtil.convertToBiggerUnits(12345));
        assertEquals("1,18 МБ", sizeUnitUtil.convertToBiggerUnits(1234567));
        assertEquals("1,15 ГБ", sizeUnitUtil.convertToBiggerUnits(1234567890));
    }
}
